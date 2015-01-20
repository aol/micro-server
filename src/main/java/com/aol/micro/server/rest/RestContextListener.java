package com.aol.micro.server.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.aol.micro.server.servers.model.ServerData;

public class RestContextListener implements ServletContextListener {

	private final ServerData serverData;

	public RestContextListener(ServerData serverData) {
		this.serverData = serverData;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		RestApplication.getResourcesMap().put(serverData.getModule().getContext(), serverData.getResources());
	}

}
