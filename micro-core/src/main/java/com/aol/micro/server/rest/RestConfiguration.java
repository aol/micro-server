package com.aol.micro.server.rest;

import java.util.Map;

import javax.servlet.http.HttpServlet;

import lombok.Value;

/**
 * Configuration for the jax-rs implementation
 * 
 * @author johnmcclean
 *
 */
@Value
public class RestConfiguration {

	private final HttpServlet  servlet;
	private final String name;
	private final String providersName;
	private final Map<String,String> initParams;
	
	public RestConfiguration(HttpServlet servlet, String name,
			String providersName, Map<String, String> initParams) {
		
		this.servlet = servlet;
		this.name = name;
		this.providersName = providersName;
		this.initParams = initParams;
	}
}
