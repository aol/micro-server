package com.aol.micro.server.servers.grizzly;

import java.util.List;

import javax.servlet.ServletContextListener;

import lombok.AllArgsConstructor;

import org.glassfish.grizzly.servlet.WebappContext;
import org.springframework.web.context.ContextLoaderListener;


import com.aol.micro.server.rest.jersey.JerseySpringIntegrationContextListener;
import com.aol.micro.server.servers.model.ServerData;

@AllArgsConstructor
public class ServletContextListenerConfigurer {
	
	private final ServerData serverData;
	private final List<ServletContextListener> listenerData;
	
	public void addListeners(WebappContext webappContext) {
	
		serverData.getRootContext()
				.getBeansOfType(ServletContextListener.class)
				.values()
				.forEach(listener -> webappContext.addListener(listener));
		listenerData.forEach(it -> webappContext.addListener(it));
	}
	
	
	
}
