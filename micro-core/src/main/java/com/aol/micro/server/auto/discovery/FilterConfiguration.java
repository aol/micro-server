package com.aol.micro.server.auto.discovery;

import java.util.Map;

import javax.servlet.Filter;

import com.google.common.collect.ImmutableMap;

public interface FilterConfiguration {
	
	public String[] getMapping();
	
	default Class<? extends Filter> getFilter(){
		return null;
	}
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return ImmutableMap.of();
	}
}
