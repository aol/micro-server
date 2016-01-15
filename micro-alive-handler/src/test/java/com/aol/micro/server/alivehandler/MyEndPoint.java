package com.aol.micro.server.alivehandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.micro.server.auto.discovery.RestResource;

@Path("/wrong_ping")
public class MyEndPoint implements RestResource {
	
	@GET
	public String value(){
		return "hello";
	}
}
