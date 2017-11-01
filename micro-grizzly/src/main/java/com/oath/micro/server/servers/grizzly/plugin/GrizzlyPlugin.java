package com.oath.micro.server.servers.grizzly.plugin;

import java.util.Optional;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.servers.ServerApplicationFactory;
import com.oath.micro.server.servers.grizzly.GrizzlyApplicationFactory;


public class GrizzlyPlugin implements Plugin{
	
	@Override
	public Optional<ServerApplicationFactory> serverApplicationFactory(){
		return Optional.of(new GrizzlyApplicationFactory());
	}
	

}
