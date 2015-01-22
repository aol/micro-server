package com.aol.micro.server;

import java.util.Map;

import javax.servlet.Filter;

import com.aol.micro.server.rest.RestResource;
import com.aol.micro.server.servers.QueryIPRetriever;
import com.google.common.collect.ImmutableMap;

public interface Module {

	default Class<? extends RestResource> getRestResourceClass() {
		return RestResource.class;
	}
	
	default Map<String,Filter> getFilters() {
		return ImmutableMap.of("/*",new QueryIPRetriever());
	}
	
	default  String getJaxWsRsApplication(){
		return "com.aol.micro.server.rest.RestApplication";
	}
	default String getProviders(){
		return "com.aol.micro.server.rest.providers";
	}
	
	public String getContext();
}
