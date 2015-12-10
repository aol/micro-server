package com.aol.micro.server.servers.grizzly;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestListener;

import org.pcollections.PStack;

import lombok.AllArgsConstructor;

import com.aol.micro.server.servers.FilterConfigurer;
import com.aol.micro.server.servers.JaxRsServletConfigurer;
import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.aol.micro.server.servers.ServletConfigurer;
import com.aol.micro.server.servers.ServletContextListenerConfigurer;
import com.aol.micro.server.servers.model.FilterData;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.servers.model.ServletData;
@AllArgsConstructor
public class TomcatListener implements ServletContainerInitializer {

	
	private final JaxRsServletConfigurer jaxRsConfigurer;
	private final ServerData serverData;

	private final PStack<FilterData> filterData;
	private final PStack<ServletData> servletData;
	private final PStack<ServletContextListener> servletContextListenerData;
	private final PStack<ServletRequestListener> servletRequestListenerData;

	
	@Override
	public void onStartup(Set<Class<?>> classes, ServletContext webappContext)
			throws ServletException {
		try {
			ServerThreadLocalVariables.getContext().set(serverData.getModule().getContext());
			new ServletContextListenerConfigurer(serverData, servletContextListenerData, servletRequestListenerData);
		     
			jaxRsConfigurer.addServlet(this.serverData,webappContext);

			new ServletConfigurer(serverData, servletData).addServlets(webappContext);

			new FilterConfigurer(serverData, this.filterData).addFilters(webappContext);
			new ServletContextListenerConfigurer(serverData, servletContextListenerData, servletRequestListenerData).addListeners(webappContext);

		}
		catch (Exception ex) {
			
		}
	}

	

}