package com.aol.micro.server.simpleserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
 
import com.aol.micro.server.auto.discovery.Rest;
 
@Rest
@Path("/foo")
public class HelloResource {
 
	@GET
	@Produces("text/plain")
	@Path("/hello")
	public String hello() {
		return "world";
	}
}