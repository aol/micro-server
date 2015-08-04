package com.aol.micro.server.auto.discovery;

import java.util.Map;

import javax.servlet.Servlet;

import org.pcollections.HashTreePMap;


public interface ServletConfiguration {

	public String[] getMapping();
	default String getName(){
		return null;
	}
	default Map<String,String> getInitParameters(){
		return HashTreePMap.empty();
	}
	default Class<? extends Servlet> getServlet(){
		return null;
	}
}
