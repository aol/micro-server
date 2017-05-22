package com.aol.micro.server.rest.swagger;

import cyclops.collections.immutable.PStackX;
import org.junit.Test;


import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;

public class SwaggerInitializerTest {

	@Test
	public void testContextInitialized() {
		SwaggerInitializer initializer = new SwaggerInitializer(ServerData.builder().resources(PStackX.of(new ServletStatusResource())).build());
		ServerData serverData = new ServerData(8080,  ImmutableList.of(new ServletStatusResource()), null, "url", () -> "context");
	
		initializer.contextInitialized(null);
	}

}

