package com.aol.micro.server.rest;

import static org.junit.Assert.*;

import org.junit.Test;

import app.com.aol.micro.server.StatusResource;

import com.google.common.collect.Lists;

import static org.hamcrest.CoreMatchers.is;
public class RestApplicationTest {

	

		@Test
		public void testDefaultConstructor() {
			RestApplication.getResourcesMap().put(Thread.currentThread().getName(), Lists.newArrayList(new StatusResource()));
			RestApplication app = new RestApplication();
			assertTrue(app.isRegistered(StatusResource.class));
			
			assertThat(app.getApplication().getClasses().iterator().next().getName(),is("com.aol.micro.server.rest.JacksonFeature"));
			
		}

		@Test
		public void testDefaultConstructorCleared() {
			RestApplication.getResourcesMap().clear();
			RestApplication app = new RestApplication();
			assertThat(app.getApplication().getClasses().iterator().next().getName(),is("com.aol.micro.server.rest.JacksonFeature"));
			assertFalse(app.isRegistered(StatusResource.class));
			
		}

		@Test
		public void testConstructor() {
			RestApplication.getResourcesMap().clear();
			RestApplication app = new RestApplication(Lists.newArrayList(new StatusResource()));
			assertThat(app.getApplication().getClasses().size(),is(1));
			assertTrue(app.isRegistered(StatusResource.class));
		}
		
	

	

	

}
