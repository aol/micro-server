package com.aol.micro.server.rest.jersey;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.google.common.collect.Lists;
public class JerseyRestApplicationTest {

	
	@Before
	public void setup(){
		ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
		JerseyRestApplication.getResourcesMap().put(Thread.currentThread().getName(), Lists.newArrayList(new ServletStatusResource()));
		JerseyRestApplication.getPackages().put(Thread.currentThread().getName(), Lists.newArrayList());
		JerseyRestApplication.getResourcesClasses().put(Thread.currentThread().getName(), Lists.newArrayList(JacksonFeature.class));
	}

		@Test
		public void testDefaultConstructor() {
			
			
			JerseyRestApplication app = new JerseyRestApplication();
			assertTrue(app.isRegistered(ServletStatusResource.class));
			
				
			assertThat(	app.getApplication().getClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()),hasItem("com.aol.micro.server.rest.jersey.JacksonFeature".intern()));
			
		}

		@Test
		public void testDefaultConstructorCleared() {
			JerseyRestApplication.getResourcesMap().clear();
			ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
			JerseyRestApplication app = new JerseyRestApplication();
			assertThat(app.getApplication().getClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()),hasItem("com.aol.micro.server.rest.jersey.JacksonFeature"));
			assertFalse(app.isRegistered(ServletStatusResource.class));
			
		}

		@Test @Ignore //fix up after spring / jersey integration
		public void testConstructor() {
			JerseyRestApplication.getResourcesMap().clear();
			JerseyRestApplication app = new JerseyRestApplication(Lists.newArrayList(new ServletStatusResource()),
					Lists.newArrayList(),Lists.newArrayList(JacksonFeature.class));
			assertThat(app.getApplication().getClasses().size(),is(1));
			assertTrue(app.isRegistered(ServletStatusResource.class));
		}
		
	

	

	

}
