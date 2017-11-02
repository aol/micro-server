package com.oath.micro.server.config;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.Rest;

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
