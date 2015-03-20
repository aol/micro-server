package com.aol.micro.server.servers.grizzly;

import javax.servlet.Servlet;

import lombok.AllArgsConstructor;

import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.auto.discovery.ServletConfiguration;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;
import com.google.common.collect.ImmutableList;

@AllArgsConstructor
public class ServletConfigurer {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final ImmutableList<ServletData> servletData;
	
	public void addServlets(WebappContext webappContext) {
		addExplicitlyDeclaredServlets(webappContext);
		addAutoDiscoveredServlets(webappContext);
	}

	private void addAutoDiscoveredServlets(WebappContext webappContext) {
		serverData
				.getRootContext()
				.getBeansOfType(ServletConfiguration.class)
				.values()
				.forEach(
						servlet -> {
							setInitParameters(
									webappContext.addServlet(getName(servlet),
											getServlet(servlet)), servlet)
									.addMapping(servlet.getMapping());
							logServlet(servlet);
						});
	}

	private void addExplicitlyDeclaredServlets(WebappContext webappContext) {
		for (ServletData servletData : servletData) {
			ServletRegistration servletReg = webappContext.addServlet(
					servletData.getServletName(), servletData.getServlet());
			servletReg.addMapping(servletData.getMapping());
			logServlet(servletData);
		}
	}

	private void logServlet(ServletData servlet) {
		logger.info("Registering {} servlet on {}",servlet.getServlet().getClass().getName(), servlet.getMapping());
		
	}

	private void logServlet(ServletConfiguration servlet) {
		logger.info("Registering {} servlet on {}",servlet.getClass().getName(), servlet.getMapping()[0]);
	}

	private Class<? extends Servlet> getServlet(ServletConfiguration servlet) {
		if (servlet.getServlet() != null)
			return servlet.getServlet();
		return (Class<? extends Servlet>) servlet.getClass();
	}

	private ServletRegistration setInitParameters(
			ServletRegistration addServlet, ServletConfiguration servlet) {
		addServlet.setInitParameters(servlet.getInitParameters());
		return addServlet;
	}

	private String getName(ServletConfiguration servlet) {
		if (servlet.getName() != null)
			return servlet.getName();
		return servlet.getClass().getName();
	}
	
}
