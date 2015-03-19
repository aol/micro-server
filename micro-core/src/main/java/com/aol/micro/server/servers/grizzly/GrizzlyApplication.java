package com.aol.micro.server.servers.grizzly;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContextListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.accesslog.AccessLogBuilder;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.ErrorCode;
import com.aol.micro.server.config.SSLProperties;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.model.AllData;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;
import com.aol.simple.react.exceptions.ExceptionSoftener;
import com.google.common.collect.ImmutableList;

@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class GrizzlyApplication  implements ServerApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ExceptionSoftener softener = ExceptionSoftener.singleton.factory.getInstance();

	@Getter
	private final ServerData serverData;
	
	private final ImmutableList<FilterData> filterData;
	private final ImmutableList<ServletData> servletData;
	private final ImmutableList<ServletContextListener> servletContextListenerData;
	@Wither
	private final SSLProperties SSLProperties;
	
	public GrizzlyApplication(AllData serverData) {
		this.serverData = serverData.getServerData();
		this.filterData = serverData.getFilterDataList();
		this.servletData = serverData.getServletDataList();
		this.servletContextListenerData = serverData.getServletContextListeners();
		this.SSLProperties = null;
	}

	public void run(CompletableFuture start,CompletableFuture end) {

		WebappContext webappContext = new WebappContext("WebappContext", "");

		new ServletContextListenerConfigurer(serverData,servletContextListenerData);
		
		addServlet(webappContext);

		new ServletConfigurer(serverData,servletData).addServlets(webappContext);

		new FilterConfigurer(serverData,this.filterData).addFilters(webappContext);

		addListeners(webappContext);

		HttpServer httpServer = HttpServer.createSimpleServer(null, "0.0.0.0",
				serverData.getPort());

		addAccessLog(httpServer);
		if(SSLProperties!=null)
			this.createSSLListener(serverData.getPort());

		startServer(webappContext, httpServer,start,end);
	}

	private void startServer(WebappContext webappContext, HttpServer httpServer, CompletableFuture start, CompletableFuture end)  {
		webappContext.deploy(httpServer);
		try {
			logger.info("Starting application {} on port {}", serverData
					.getModule().getContext(), serverData.getPort());
			logger.info("Browse to http://localhost:{}/{}/application.wadl",
					serverData.getPort(), serverData.getModule().getContext());
			logger.info("Configured resource classes :-");
			serverData.extractResources()
						.forEach(t -> logger.info(t.v1 + " : " + "http://localhost:"+serverData.getPort()
													+"/"+serverData.getModule().getContext() + t.v2 ));;
			httpServer.start();
			start.complete(true);
			end.get();
			
		} catch (IOException e) {
			softener.throwSoftenedException(e);
		} catch (ExecutionException e) {
			softener.throwSoftenedException(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			softener.throwSoftenedException(e);
		} finally {
			httpServer.stop();
		}
	}

	private void addAccessLog(HttpServer httpServer) {
		try {
			String accessLogLocation = serverData.getRootContext()
					.getBean(AccessLogLocationBean.class)
					.getAccessLogLocation();
			final AccessLogBuilder builder = new AccessLogBuilder(
					accessLogLocation + serverData.getModule().getContext()
							+ "-access.log");
			builder.rotatedDaily();
			builder.rotationPattern("yyyy-MM-dd");
			builder.instrument(httpServer.getServerConfiguration());
		} catch (Exception e) {
			logger.error(ErrorCode.SERVER_STARTUP_FAILED_TO_CREATE_ACCESS_LOG
					.toString() + ": " + e.getMessage());
			if (e.getCause() != null)
				logger.error("CAUSED BY: "
						+ ErrorCode.SERVER_STARTUP_FAILED_TO_CREATE_ACCESS_LOG
								.toString() + ": " + e.getCause().getMessage());

		}

	}

	private void addServlet(WebappContext webappContext) {
		ServletContainer container = new ServletContainer();
		ServletRegistration servletRegistration = webappContext.addServlet(
				"Jersey Spring Web Application", container);
		servletRegistration.setInitParameter("javax.ws.rs.Application",
				this.serverData.getModule().getJaxWsRsApplication());
		servletRegistration.setInitParameter(
				"jersey.config.server.provider.packages", this.serverData
						.getModule().getProviders());
		servletRegistration.setLoadOnStartup(1);
		servletRegistration.addMapping(serverData.getBaseUrlPattern());
	}



	private NetworkListener createSSLListener(int port){
		
		
		
		SSLConfigurationBuilder sslBuilder = new SSLConfigurationBuilder();
		NetworkListener listener = new NetworkListener("grizzly", "0.0.0.0", Integer.valueOf(port));
		listener.getFileCache().setEnabled(false);
		
			listener.setSecure(true);
			listener.setSSLEngineConfig(sslBuilder.build(SSLProperties));
		
		return listener;
	}

	private void addListeners(WebappContext webappContext) {
		
		new ServletContextListenerConfigurer(serverData, servletContextListenerData).addListeners(webappContext);
	}

}
