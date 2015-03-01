package com.aol.micro.server.config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.auto.discovery.Rest;

@Microserver
@Rest
@Path("/test")
public class SimpleApp {

	public static void main(String[] args){
		new MicroserverApp(()->"hello-world").run();
	}
	
	@GET
	public String test(){
		return "ok!";
	}
}
