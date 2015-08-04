package com.aol.micro.server.rest.jersey;

import java.util.HashMap;

import org.glassfish.jersey.servlet.ServletContainer;

import com.aol.micro.server.rest.RestConfiguration;

public class ConfigureMainServlet {
	public  RestConfiguration servletConfig() {
		
		return new RestConfiguration(new ServletContainer(),"Jersey Spring Web Application","jersey.config.server.provider.packages",new HashMap<>());
		
	}
}
