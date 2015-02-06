package com.aol.micro.server.rest;

import org.junit.Test;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.aol.micro.server.rest.swagger.SwaggerInitializer;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;

public class SwaggerInitializerTest {

	@Test
	public void testContextInitialized() {
		SwaggerInitializer initializer = new SwaggerInitializer(ServerData.builder().resources(ImmutableList.of(new ServletStatusResource())).build());
		ServerData serverData = new ServerData(8080,  ImmutableList.of(new ServletStatusResource()), null, "url", () -> "context");
	
		initializer.contextInitialized(null);
	}

}

