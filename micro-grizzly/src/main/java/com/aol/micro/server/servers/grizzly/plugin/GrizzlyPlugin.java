package com.aol.micro.server.servers.grizzly.plugin;

import java.util.Optional;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.ServerApplicationFactory;
import com.aol.micro.server.servers.grizzly.GrizzlyApplicationFactory;


public class GrizzlyPlugin implements Plugin{
	
	@Override
	public Optional<ServerApplicationFactory> serverApplicationFactory(){
		return Optional.of(new GrizzlyApplicationFactory());
	}
	

}
