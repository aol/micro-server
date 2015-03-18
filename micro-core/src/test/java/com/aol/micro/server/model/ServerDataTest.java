package com.aol.micro.server.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import app.servlet.com.aol.micro.server.ServletStatusResource;

import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;

public class ServerDataTest {

	private ServerData serverData;
	private AnnotationConfigWebApplicationContext rootContext;

	@Before
	public void setUp() {
		rootContext = mock(AnnotationConfigWebApplicationContext.class);
		serverData = new ServerData(8080,  Lists.newArrayList(new ServletStatusResource()), rootContext, "url", 
				()->"context");
	}

	@Test
	public void testGetters() {
		assertThat(serverData.getBaseUrlPattern(), is("url"));
		assertThat(serverData.getResources().size(), is(1));
		assertThat(serverData.getRootContext(), is(rootContext));
		assertThat(serverData.getModule().getContext(), is("context"));
		assertThat(serverData.getPort(), is(8080));
	}
	
	@Test(expected=NullPointerException.class)
	public void testLogNull(){
		
		serverData = new ServerData(8080,  Lists.newArrayList((Object)null), rootContext, "url", 
				()->"context");
		serverData.logResources("url");
		
	}
	
	@Test
	public void testLog(){
		
		
		serverData.logResources("http://localhost:8080/hello");
		
	}

	
}
