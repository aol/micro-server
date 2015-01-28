package com.aol.micro.server.rest;

import org.junit.Test;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.aol.micro.server.rest.swagger.SwaggerInitializer;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;

public class SwaggerInitializerTest {

	@Test
	public void testContextInitialized() {
		SwaggerInitializer initializer = new SwaggerInitializer(Lists.newArrayList(new ServletStatusResource()));
		ServerData serverData = new ServerData(8080, null, null, Lists.newArrayList(new ServletStatusResource()), null, "url", () -> "context");
		initializer.setServerData(serverData);
		initializer.contextInitialized(null);
	}

}

