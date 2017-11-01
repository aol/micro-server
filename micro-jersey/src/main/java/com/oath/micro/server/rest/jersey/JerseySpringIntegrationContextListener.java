package com.oath.micro.server.rest.jersey;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.oath.micro.server.servers.model.ServerData;
import cyclops.collections.mutable.ListX;

public class JerseySpringIntegrationContextListener implements ServletContextListener {

	private final ServerData serverData;

	public JerseySpringIntegrationContextListener(ServerData serverData) {
		this.serverData = serverData;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		JerseyRestApplication.getResourcesMap().put(serverData.getModule().getContext(), ListX.fromIterable(serverData.getResources()));
		JerseyRestApplication.getPackages().put(serverData.getModule().getContext(), serverData.getModule().getDefaultJaxRsPackages());
		JerseyRestApplication.getResourcesClasses().put(serverData.getModule().getContext(), serverData.getModule().getDefaultResources());
		JerseyRestApplication.getResourceConfigManager().put(serverData.getModule().getContext(), serverData.getModule().getResourceConfigManager());
		JerseyRestApplication.getServerPropertyMap().put(serverData.getModule().getContext(), serverData.getModule().getServerProperties());
	}

}
