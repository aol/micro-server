package com.aol.micro.server.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import app.com.aol.micro.server.StatusResource;

import com.aol.micro.server.module.Module;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;

public class RestContextListenerTest {

	private JerseySpringIntegrationContextListener restContextListener;
	private StatusResource statsResource;

	@Before
	public void setUp() {
		statsResource = new StatusResource();
		ServerData serverData = new ServerData(8080, null, null, Lists.newArrayList(statsResource), null, "baseUrl", () -> "test");
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
