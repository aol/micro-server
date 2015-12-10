package com.aol.micro.server.servers.tomcat;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.catalina.valves.Constants;
import org.pcollections.PStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.cyclops.invokedynamic.ExceptionSoftener;
import com.aol.micro.server.ErrorCode;
import com.aol.micro.server.config.SSLProperties;
import com.aol.micro.server.module.WebServerProvider;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.servers.JaxRsServletConfigurer;
import com.aol.micro.server.servers.ServerApplication;
import com.aol.micro.server.servers.ServletContextListenerConfigurer;
import com.aol.micro.server.servers.model.AllData;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TomcatApplication implements ServerApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Getter
	private final ServerData serverData;

	private final PStack<FilterData> filterData;
	private final PStack<ServletData> servletData;
	private final PStack<ServletContextListener> servletContextListenerData;
	private final PStack<ServletRequestListener> servletRequestListenerData;
	@Wither
	private final SSLProperties SSLProperties;

	public TomcatApplication(AllData serverData) {
		this.serverData = serverData.getServerData();
		this.filterData = serverData.getFilterDataList();
		this.servletData = serverData.getServletDataList();
		this.servletContextListenerData = serverData.getServletContextListeners();
		this.servletRequestListenerData = serverData.getServletRequestListeners();
		this.SSLProperties = null;
	}

	public void run(CompletableFuture start,  JaxRsServletConfigurer jaxRsConfigurer, CompletableFuture end) {
		 Tomcat tomcat = new Tomcat();
		 tomcat.setPort(serverData.getPort());
		 File docBase = new File(".");
		 StandardContext context =(StandardContext)tomcat.addContext("", docBase.getAbsolutePath());
		context.addServletContainerInitializer(new TomcatListener(jaxRsConfigurer, serverData, filterData, servletData, servletContextListenerData, servletRequestListenerData), 
				new HashSet<>());
		addAccessLog(tomcat,context);
	
		serverData.getModule().getServerConfigManager().accept(new WebServerProvider(tomcat));
		
	

		startServer( tomcat, start, end);
	}

	private void startServer( Tomcat httpServer, CompletableFuture start, CompletableFuture end) {
		//webappContext.deploy(httpServer);
		try {
			logger.info("Starting application {} on port {}", serverData.getModule().getContext(), serverData.getPort());
			logger.info("Browse to http://localhost:{}/{}/application.wadl", serverData.getPort(), serverData.getModule().getContext());
			logger.info("Configured resource classes :-");
			serverData.extractResources().forEach(
					t -> logger.info(t.v1() + " : " + "http://localhost:" + serverData.getPort() + "/" + serverData.getModule().getContext() + t.v2()));
			;
			
				httpServer.start();
			
			start.complete(true);
			end.get();

		}catch (LifecycleException e) {
			 throw ExceptionSoftener.throwSoftenedException(e);
		} catch (ExecutionException e) {
			throw ExceptionSoftener.throwSoftenedException(e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw ExceptionSoftener.throwSoftenedException(e);
		} finally {
			try {
				httpServer.stop();
				httpServer.destroy();
				
					Thread.sleep(3_000);
				
			} catch (LifecycleException e) {
				
			}catch (InterruptedException e) {
				
			}
		}
	}

	private void addAccessLog(Tomcat httpServer, StandardContext context) {
		try {
			 
			String accessLogLocation = serverData.getRootContext().getBean(AccessLogLocationBean.class).getAccessLogLocation();

			accessLogLocation = accessLogLocation + "/" + replaceSlash(serverData.getModule().getContext()) + "-access.log";
			
			AccessLogValve accessLogValve = new AccessLogValve();
            accessLogValve.setDirectory(accessLogLocation);
            accessLogValve.setPattern(Constants.AccessLog.COMMON_ALIAS);
            accessLogValve.setSuffix(".log");
            accessLogValve.setRotatable(true);
            context.getPipeline().addValve(accessLogValve);
			
		} catch (Exception e) {
			
			logger.error(ErrorCode.SERVER_STARTUP_FAILED_TO_CREATE_ACCESS_LOG.toString() + ": " + e.getMessage());
			if (e.getCause() != null)
				logger.error("CAUSED BY: " + ErrorCode.SERVER_STARTUP_FAILED_TO_CREATE_ACCESS_LOG.toString() + ": " + e.getCause().getMessage());

		}

	}
	
	

	private String replaceSlash(String context) {
		if (context != null && context.contains("/")) {
			return context.substring(0, context.indexOf("/"));
		}
		return context;
	}

}
