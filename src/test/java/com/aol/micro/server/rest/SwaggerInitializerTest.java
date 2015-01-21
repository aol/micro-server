package com.aol.micro.server.rest;

import org.junit.Test;

import com.aol.micro.server.app.StatusResource;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;

public class SwaggerInitializerTest {

	@Test
	public void testContextInitialized() {
		SwaggerInitializer initializer = new SwaggerInitializer(Lists.newArrayList(new StatusResource()));
		ServerData serverData = new ServerData(8080, null, Lists.newArrayList(new StatusResource()), null, "url", () -> "context");
		initializer.setServerData(serverData);
		initializer.contextInitialized(null);
	}

}

