package com.aol.micro.server.web;

import java.util.Map;

import javax.servlet.Servlet;

import com.google.common.collect.ImmutableMap;


public interface ServletConfiguration {

	public String[] getMapping();
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return ImmutableMap.of();
	}
	default Class<? extends Servlet> getServlet(){
		return null;
	}
}
