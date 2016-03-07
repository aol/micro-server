package com.aol.micro.server.rest.jersey;
import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;


import com.aol.micro.server.servers.ServerThreadLocalVariables;

public class CustomJerseyServlet extends ServletContainer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String context;
	public CustomJerseyServlet(String context){
		this.context =context;
	}
	 @Override
	   public void init() throws ServletException {
		 ServerThreadLocalVariables.getContext().set(context);
		 super.init();
	        
	   }
}
