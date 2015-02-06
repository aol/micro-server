package com.aol.micro.server.module;

import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.rest.jersey.JerseyRestApplication;
import com.aol.micro.server.rest.jersey.JerseySpringIntegrationContextListener;
import com.aol.micro.server.rest.swagger.SwaggerInitializer;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.web.filter.QueryIPRetriever;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public interface Module {

	default Class<? extends RestResource> getRestResourceClass() {
		return RestResource.class;
	}
	
	default List<ServletContextListener> getListeners(ServerData data){
		return ImmutableList.of(new ContextLoaderListener(data
				.getRootContext()),
				new JerseySpringIntegrationContextListener(data),
				new SwaggerInitializer(data));
	}
	default Map<String,Filter> getFilters(ServerData data) {
		return ImmutableMap.of("/*",new QueryIPRetriever());
	}
	default Map<String,Servlet> getServlets(ServerData data) {
		return ImmutableMap.of();
	}
	
	default  String getJaxWsRsApplication(){
		return JerseyRestApplication.class.getCanonicalName();
	}
	default String getProviders(){
		return "com.aol.micro.server.rest.providers";
	}
	
	public String getContext();
}
