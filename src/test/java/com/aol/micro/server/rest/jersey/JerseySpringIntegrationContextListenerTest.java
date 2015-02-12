package com.aol.micro.server.rest.jersey;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;

public class JerseySpringIntegrationContextListenerTest {
	
	JerseySpringIntegrationContextListener listener;
	ServerData serverData;
	@Before
	public void setup (){
		serverData = ServerData.builder().module(()->"hello").resources(ImmutableList.of()).build();
		listener = new JerseySpringIntegrationContextListener(serverData);
	}
	

	@Test
	public void testContextInitialized() {
		listener.contextInitialized(null);
		
		assertThat(JerseyRestApplication.getResourcesMap().get(serverData.getModule().getContext())
				, is(serverData.getResources()));
		assertThat(JerseyRestApplication.getPackages().get(serverData.getModule().getContext()),is( serverData.getModule().getDefaultJaxRsPackages()));
		assertThat(JerseyRestApplication.getResourcesClasses().get(serverData.getModule().getContext()), 
				is(serverData.getModule().getDefaultResources()));
	}

}
