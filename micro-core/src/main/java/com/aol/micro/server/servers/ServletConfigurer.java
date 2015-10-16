package com.aol.micro.server.servers;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import lombok.AllArgsConstructor;




import org.pcollections.PStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.auto.discovery.ServletConfiguration;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;

@AllArgsConstructor
public class ServletConfigurer {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final PStack<ServletData> servletData;
	
	public void addServlets(ServletContext webappContext) {
		addExplicitlyDeclaredServlets(webappContext);
		addAutoDiscoveredServlets(webappContext);
	}

	private void addAutoDiscoveredServlets(ServletContext webappContext) {
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

	private void addExplicitlyDeclaredServlets(ServletContext webappContext) {
		for (ServletData servletData : servletData) {
			ServletRegistration.Dynamic servletReg = webappContext.addServlet(
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

	private ServletRegistration.Dynamic setInitParameters(
			ServletRegistration.Dynamic addServlet, ServletConfiguration servlet) {
		addServlet.setInitParameters(servlet.getInitParameters());
		return addServlet;
	}

	private String getName(ServletConfiguration servlet) {
		if (servlet.getName() != null)
			return servlet.getName();
		return servlet.getClass().getName();
	}
	
}
