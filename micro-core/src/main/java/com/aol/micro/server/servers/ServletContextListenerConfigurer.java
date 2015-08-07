package com.aol.micro.server.servers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.servers.model.ServerData;

@AllArgsConstructor
public class ServletContextListenerConfigurer {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final List<ServletContextListener> listenerData;
	private final List<ServletRequestListener> listenerRequestData;

	public void addListeners(ServletContext webappContext) {
	
		serverData.getRootContext()
				.getBeansOfType(ServletContextListener.class)
				.values()
				
				.stream()
				
				.peek(this::logListener)
				.forEach(listener -> webappContext.addListener(listener));
		listenerData.forEach(it -> webappContext.addListener(it));

		serverData.getRootContext()
				.getBeansOfType(ServletRequestListener.class)
				.values()
				.stream()
				.peek(this::logListener)
				.forEach(listener -> webappContext.addListener(listener));
		listenerRequestData.forEach(it -> webappContext.addListener(it));

	}
	private void logListener(ServletContextListener listener) {
		logger.info("Registering servlet context listener {}", listener.getClass().getName());
		
	}
	private void logListener(ServletRequestListener listener) {
		logger.info("Registering servlet request listener {}",listener.getClass().getName());

	}
	
	
}
