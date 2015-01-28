package com.aol.micro.server.rest;

import static org.junit.Assert.*;

import org.junit.Test;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.google.common.collect.Lists;

import static org.hamcrest.CoreMatchers.is;
public class RestApplicationTest {

	

		@Test
		public void testDefaultConstructor() {
			ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
			JerseyRestApplication.getResourcesMap().put(Thread.currentThread().getName(), Lists.newArrayList(new ServletStatusResource()));
			JerseyRestApplication app = new JerseyRestApplication();
			assertTrue(app.isRegistered(ServletStatusResource.class));
			
			assertThat(app.getApplication().getClasses().iterator().next().getName(),is("com.aol.micro.server.rest.JacksonFeature"));
			
		}

		@Test
		public void testDefaultConstructorCleared() {
			JerseyRestApplication.getResourcesMap().clear();
			ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
			JerseyRestApplication app = new JerseyRestApplication();
			assertThat(app.getApplication().getClasses().iterator().next().getName(),is("com.aol.micro.server.rest.JacksonFeature"));
			assertFalse(app.isRegistered(ServletStatusResource.class));
			
		}

		@Test
		public void testConstructor() {
			JerseyRestApplication.getResourcesMap().clear();
			JerseyRestApplication app = new JerseyRestApplication(Lists.newArrayList(new ServletStatusResource()));
			assertThat(app.getApplication().getClasses().size(),is(1));
			assertTrue(app.isRegistered(ServletStatusResource.class));
		}
		
	

	

	

}
