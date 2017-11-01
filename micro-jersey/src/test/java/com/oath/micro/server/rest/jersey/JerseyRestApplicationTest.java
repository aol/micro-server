package com.oath.micro.server.rest.jersey;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.oath.micro.server.rest.jackson.JacksonFeature;
import com.oath.micro.server.servers.ServerThreadLocalVariables;
public class JerseyRestApplicationTest {

	
	@Before
	public void setup(){
		ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
		JerseyRestApplication.getResourcesMap().put(Thread.currentThread().getName(), Arrays.asList(new ServletStatusResource()));
		JerseyRestApplication.getPackages().put(Thread.currentThread().getName(), Arrays.asList());
		JerseyRestApplication.getResourcesClasses().put(Thread.currentThread().getName(), Arrays.asList(JacksonFeature.class));
		JerseyRestApplication.getServerPropertyMap().put(Thread.currentThread().getName(), new HashMap<>());
	}

		@Test
		public void testDefaultConstructor() {			
			JerseyRestApplication app = new JerseyRestApplication();
			assertTrue(app.isRegistered(ServletStatusResource.class));				
			assertThat(	app.getApplication().getClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()),hasItem("com.aol.micro.server.rest.jackson.JacksonFeature".intern()));
			
		}

		@Test
		public void testDefaultConstructorCleared() {
			JerseyRestApplication.getResourcesMap().clear();
			ServerThreadLocalVariables.getContext().set(Thread.currentThread().getName());
			JerseyRestApplication app = new JerseyRestApplication();
			assertThat(app.getApplication().getClasses().stream().map(c -> c.getName()).collect(Collectors.toSet()),hasItem("com.aol.micro.server.rest.jackson.JacksonFeature"));
			assertFalse(app.isRegistered(ServletStatusResource.class));
			
		}

		@Test @Ignore //fix up after spring / jersey integration
		public void testConstructor() {
			JerseyRestApplication.getResourcesMap().clear();
			JerseyRestApplication app = new JerseyRestApplication(Arrays.asList(new ServletStatusResource()),
					Arrays.asList(),Arrays.asList(JacksonFeature.class), new HashMap<>());
			assertThat(app.getApplication().getClasses().size(),is(1));
			assertTrue(app.isRegistered(ServletStatusResource.class));
		}
		
	

	

	

}
