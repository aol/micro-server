package com.oath.micro.server.servers.grizzly;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import com.oath.cyclops.types.persistent.PersistentList;
import com.oath.cyclops.util.ExceptionSoftener;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.accesslog.AccessLogBuilder;
import org.glassfish.grizzly.servlet.WebappContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.oath.micro.server.InternalErrorCode;
import com.oath.micro.server.config.SSLProperties;
import com.oath.micro.server.module.WebServerProvider;
import com.oath.micro.server.servers.AccessLogLocationBean;
import com.oath.micro.server.servers.FilterConfigurer;
import com.oath.micro.server.servers.JaxRsServletConfigurer;
import com.oath.micro.server.servers.ServerApplication;
import com.oath.micro.server.servers.ServletConfigurer;
import com.oath.micro.server.servers.ServletContextListenerConfigurer;
import com.oath.micro.server.servers.model.AllData;
import com.oath.micro.server.servers.model.FilterData;
import com.oath.micro.server.servers.model.ServerData;
import com.oath.micro.server.servers.model.ServletData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GrizzlyApplication implements ServerApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Getter
	private final ServerData serverData;

	private final PersistentList<FilterData> filterData;
	private final PersistentList<ServletData> servletData;
	private final PersistentList<ServletContextListener> servletContextListenerData;
	private final PersistentList<ServletRequestListener> servletRequestListenerData;
	
	public GrizzlyApplication(AllData serverData) {
		this.serverData = serverData.getServerData();
		this.filterData = serverData.getFilterDataList();
		this.servletData = serverData.getServletDataList();
		this.servletContextListenerData = serverData.getServletContextListeners();
		this.servletRequestListenerData = serverData.getServletRequestListeners();		
	}

	public void run(CompletableFuture start,  JaxRsServletConfigurer jaxRsConfigurer, CompletableFuture end) {

		WebappContext webappContext = new WebappContext("WebappContext", "");

		new ServletContextListenerConfigurer(serverData, servletContextListenerData, servletRequestListenerData);

		
		jaxRsConfigurer.addServlet(this.serverData,webappContext);

		new ServletConfigurer(serverData, servletData).addServlets(webappContext);

		new FilterConfigurer(serverData, this.filterData).addFilters(webappContext);

		addListeners(webappContext);

		HttpServer httpServer = HttpServer.createSimpleServer(null, "0.0.0.0", serverData.getPort());
		serverData.getModule().getServerConfigManager().accept(new WebServerProvider(httpServer));
		addAccessLog(httpServer);
		addSSL(httpServer);

		startServer(webappContext, httpServer, start, end);
	}
	
	private void addSSL(HttpServer httpServer) {
		SSLProperties sslProperties = serverData.getRootContext().getBean(SSLProperties.class);
		if (sslProperties != null) {
			httpServer.addListener(this.createSSLListener(serverData.getPort(), sslProperties));
		}
	}

	private void startServer(WebappContext webappContext, HttpServer httpServer, CompletableFuture start, CompletableFuture end) {
		webappContext.deploy(httpServer);
		try {
			logger.info("Starting application {} on port {}", serverData.getModule().getContext(), serverData.getPort());
			logger.info("Browse to http://localhost:{}/{}/application.wadl", serverData.getPort(), serverData.getModule().getContext());
			logger.info("Configured resource classes :-");
			serverData.extractResources()
					.forEach(t -> logger.info(t._1() + " : " + "http://localhost:" + serverData.getPort() + "/" + serverData.getModule().getContext() + t._2()));
			;
			httpServer.start();
			start.complete(true);
			end.get();

		} catch (IOException e) {
			throw ExceptionSoftener.throwSoftenedException(e);
		} catch (ExecutionException e) {
			throw ExceptionSoftener.throwSoftenedException(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw ExceptionSoftener.throwSoftenedException(e);
		} finally {
			httpServer.stop();
		}
	}

	private void addAccessLog(HttpServer httpServer) {
		try {
			String accessLogLocation = serverData.getRootContext().getBean(AccessLogLocationBean.class).getAccessLogLocation();

			accessLogLocation = accessLogLocation + "/" + replaceSlash(serverData.getModule().getContext()) + "-access.log";
			final AccessLogBuilder builder = new AccessLogBuilder(accessLogLocation);

			builder.rotatedDaily();
			builder.rotationPattern("yyyy-MM-dd");
			builder.instrument(httpServer.getServerConfiguration());
		} catch (Exception e) {
			logger.error(InternalErrorCode.SERVER_STARTUP_FAILED_TO_CREATE_ACCESS_LOG.toString() + ": " + e.getMessage());
			if (e.getCause() != null)
				logger.error("CAUSED BY: " + InternalErrorCode.SERVER_STARTUP_FAILED_TO_CREATE_ACCESS_LOG.toString() + ": " + e.getCause().getMessage());

		}
	}
	

	private NetworkListener createSSLListener(int port, SSLProperties sslProperties) {

		SSLConfigurationBuilder sslBuilder = new SSLConfigurationBuilder();
		NetworkListener listener = new NetworkListener("grizzly", "0.0.0.0", Integer.valueOf(port));
		listener.getFileCache().setEnabled(false);

		listener.setSecure(true);
		listener.setSSLEngineConfig(sslBuilder.build(sslProperties));

		return listener;
	}

	private void addListeners(WebappContext webappContext) {
		new ServletContextListenerConfigurer(serverData, servletContextListenerData, servletRequestListenerData).addListeners(webappContext);
	}

	private String replaceSlash(String context) {
		if (context != null && context.contains("/")) {
			return context.substring(0, context.indexOf("/"));
		}
		return context;
	}

}
