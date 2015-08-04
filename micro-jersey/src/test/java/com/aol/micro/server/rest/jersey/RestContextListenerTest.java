package com.aol.micro.server.rest.jersey;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


import com.aol.micro.server.rest.jersey.JerseyRestApplication;
import com.aol.micro.server.rest.jersey.JerseySpringIntegrationContextListener;
import com.aol.micro.server.servers.model.ServerData;

public class RestContextListenerTest {

	private JerseySpringIntegrationContextListener restContextListener;
	private ServletStatusResource statsResource;

	@Before
	public void setUp() {
		statsResource = new ServletStatusResource();
		ServerData serverData = new ServerData(8080, Arrays.asList(statsResource), null, "baseUrl", () -> "test");
		restContextListener = new JerseySpringIntegrationContextListener(serverData);
	}

	@Test
	public void testContextInitialized() {
		restContextListener.contextInitialized(null);
		assertThat(JerseyRestApplication.getResourcesMap().get("test").get(0), is(statsResource));
	}

	@Test
	public void testContextDestroyed() {
		restContextListener.contextDestroyed(null);
	}

}
