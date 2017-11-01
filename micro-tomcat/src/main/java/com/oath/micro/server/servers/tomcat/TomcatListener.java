package com.oath.micro.server.servers.tomcat;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestListener;


import com.oath.cyclops.types.persistent.PersistentList;
import lombok.AllArgsConstructor;

import com.oath.micro.server.servers.FilterConfigurer;
import com.oath.micro.server.servers.JaxRsServletConfigurer;
import com.oath.micro.server.servers.ServerThreadLocalVariables;
import com.oath.micro.server.servers.ServletConfigurer;
import com.oath.micro.server.servers.ServletContextListenerConfigurer;
import com.oath.micro.server.servers.model.FilterData;
import com.oath.micro.server.servers.model.ServerData;
import com.oath.micro.server.servers.model.ServletData;
@AllArgsConstructor
public class TomcatListener implements ServletContainerInitializer {

	
	private final JaxRsServletConfigurer jaxRsConfigurer;
	private final ServerData serverData;

	private final PersistentList<FilterData> filterData;
	private final PersistentList<ServletData> servletData;
	private final PersistentList<ServletContextListener> servletContextListenerData;
	private final PersistentList<ServletRequestListener> servletRequestListenerData;

	
	@Override
	public void onStartup(Set<Class<?>> classes, ServletContext webappContext)
			throws ServletException {
		try {
			ServerThreadLocalVariables.getContext().set(serverData.getModule().getContext());
		     
			jaxRsConfigurer.addServlet(this.serverData,webappContext);

			new ServletConfigurer(serverData, servletData).addServlets(webappContext);

			new FilterConfigurer(serverData, this.filterData).addFilters(webappContext);
			new ServletContextListenerConfigurer(serverData, servletContextListenerData, servletRequestListenerData).addListeners(webappContext);

		}
		catch (Exception ex) {
			
		}
	}

	

}