package com.aol.micro.server.rest.jersey;

import java.util.HashMap;

import org.glassfish.jersey.servlet.ServletContainer;

import com.aol.micro.server.rest.RestConfiguration;
import com.aol.micro.server.servers.ServerThreadLocalVariables;

public class ConfigureMainServlet {
	public  RestConfiguration servletConfig() {
		
		return new RestConfiguration(new CustomJerseyServlet(ServerThreadLocalVariables.getContext().get()),"Jersey Spring Web Application","jersey.config.server.provider.packages",new HashMap<>());
		
	}
}
