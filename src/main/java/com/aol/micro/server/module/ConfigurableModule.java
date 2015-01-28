package com.aol.micro.server.module;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;
import lombok.experimental.Wither;

import com.aol.micro.server.auto.discovery.RestResource;


@Builder
@AllArgsConstructor
@Wither
public class ConfigurableModule implements Module{
	
	private final RestResource resource;
	private final Map<String, Filter> filters;
	private final Map<String, Servlet> servlets;
	private final String jaxWsRsApplication;
	private final String providers;
	private final String context;
	
	@Override
	public Class<? extends RestResource> getRestResourceClass() {
		if(resource!=null)
			return resource.getClass();
		
		return Module.super.getRestResourceClass();
	}

	@Override
	public Map<String, Filter> getFilters() {
		if(filters!=null)
			return filters;
		return Module.super.getFilters();
	}

	@Override
	public Map<String, Servlet> getServlets() {
		if(servlets!=null)
			return servlets;
		return Module.super.getServlets();
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

	
}
