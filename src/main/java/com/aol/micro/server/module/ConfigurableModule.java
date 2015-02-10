package com.aol.micro.server.module;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.servers.model.ServerData;


@Builder
@AllArgsConstructor
@Wither
public class ConfigurableModule implements Module{
	
	private final List<Class<? extends RestResource>> resourceClasses;
	private final List<Class> defaultResources;
	private final List<ServletContextListener> listeners;
	private final Map<String, Filter> filters;
	private final Map<String, Servlet> servlets;
	private final String jaxWsRsApplication;
	private final String providers;
	private final String context;
	private final Set<Class> springConfigurationClasses;
	
	@Override
	public List<Class<? extends RestResource>> getRestResourceClasses() {
		if(resourceClasses!=null)
			return resourceClasses;
		
		return Module.super.getRestResourceClasses();
	}
	
	@Override
	public List<Class> getDefaultResources() {
		if(this.defaultResources!=null)
			return this.defaultResources;
		return Module.super.getDefaultResources();
	}

	@Override
	public List<ServletContextListener> getListeners(ServerData data) {
		if(listeners!=null)
			return listeners;
		return Module.super.getListeners(data);
	}
	
	@Override
	public Map<String, Filter> getFilters(ServerData data) {
		if(filters!=null)
			return filters;
		return Module.super.getFilters(data);
	}

	@Override
	public Map<String, Servlet> getServlets(ServerData data) {
		if(servlets!=null)
			return servlets;
		return Module.super.getServlets(data);
	}

	@Override
	public String getJaxWsRsApplication() {
		if(this.jaxWsRsApplication!=null)
			return jaxWsRsApplication;
		return Module.super.getJaxWsRsApplication();
	}

	@Override
	public String getProviders() {
		if(providers!=null)
			return providers;
		return Module.super.getProviders();
	}

	@Override
	public String getContext() {
		
		return context;
	}

	@Override
	public Set<Class> getSpringConfigurationClasses() {
		if(this.springConfigurationClasses!=null)
			return this.springConfigurationClasses;
		return Module.super.getSpringConfigurationClasses();
	}

	

	
}
