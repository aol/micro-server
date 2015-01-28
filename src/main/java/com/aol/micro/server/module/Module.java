package com.aol.micro.server.module;

import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.Servlet;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.rest.JerseyRestApplication;
import com.aol.micro.server.web.filter.QueryIPRetriever;
import com.google.common.collect.ImmutableMap;

public interface Module {

	default Class<? extends RestResource> getRestResourceClass() {
		return RestResource.class;
	}
	
	default Map<String,Filter> getFilters() {
		return ImmutableMap.of("/*",new QueryIPRetriever());
	}
	default Map<String,Servlet> getServlets() {
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
