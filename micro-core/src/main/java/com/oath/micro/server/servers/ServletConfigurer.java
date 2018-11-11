package com.oath.micro.server.servers;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import com.oath.cyclops.types.persistent.PersistentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oath.micro.server.auto.discovery.ServletConfiguration;
import com.oath.micro.server.servers.model.ServerData;
import com.oath.micro.server.servers.model.ServletData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServletConfigurer {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ServerData serverData;
	private final PersistentList<ServletData> servletData;
	
	public void addServlets(ServletContext webappContext) {
		addExplicitlyDeclaredServlets(webappContext);
		addAutoDiscoveredServlets(webappContext);
	}
	private void handleServlet(ServletConfiguration servlet,ServletContext webappContext){
		servlet.getServlet().fold(clazz-> {
			setInitParameters(webappContext.addServlet(getName(servlet),
						clazz), servlet)
				.addMapping(servlet.getMapping());
			return 1; 
			}, obj-> {
				ServletRegistration.Dynamic servletReg = webappContext.addServlet(
						servlet.getName(), obj);
				servletReg.addMapping(servlet.getMapping());
							return 2;
						});
	}
	private void addAutoDiscoveredServlets(ServletContext webappContext) {
		serverData
				.getRootContext()
				.getBeansOfType(ServletConfiguration.class)
				.values()
				.forEach(servlet -> handleServlet(servlet,webappContext));
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
