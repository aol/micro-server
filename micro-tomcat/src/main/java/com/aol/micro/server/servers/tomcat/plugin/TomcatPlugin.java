package com.aol.micro.server.servers.tomcat.plugin;

import java.util.Optional;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.ServerApplicationFactory;
import com.aol.micro.server.servers.tomcat.TomcatApplicationFactory;


public class TomcatPlugin implements Plugin{
	
	@Override
	public Optional<ServerApplicationFactory> serverApplicationFactory(){
		return Optional.of(new TomcatApplicationFactory());
	}
	

}
