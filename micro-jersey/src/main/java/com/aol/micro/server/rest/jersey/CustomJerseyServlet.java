package com.aol.micro.server.rest.jersey;
import javax.servlet.ServletException;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.WebServletConfig;

import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.sun.corba.se.spi.activation.Server;
public class CustomJerseyServlet extends ServletContainer{

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
