package com.oath.micro.server.rest.jersey;

import java.util.HashMap;

import com.oath.micro.server.rest.RestConfiguration;
import com.oath.micro.server.servers.ServerThreadLocalVariables;

public class ConfigureMainServlet {
	public  RestConfiguration servletConfig() {
		
		return new RestConfiguration(new CustomJerseyServlet(ServerThreadLocalVariables.getContext().get()),"Jersey Spring Web Application","jersey.config.server.provider.packages",new HashMap<>());
		
	}
}
