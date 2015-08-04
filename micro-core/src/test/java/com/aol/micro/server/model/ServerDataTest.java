package com.aol.micro.server.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.stream.Collectors;



import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.servers.model.ServerData;

public class ServerDataTest {

	private ServerData serverData;
	private AnnotationConfigWebApplicationContext rootContext;

	@Before
	public void setUp() {
		rootContext = mock(AnnotationConfigWebApplicationContext.class);
		serverData = new ServerData(8080,  Arrays.asList(new ServletStatusResource()), rootContext, "url", 
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
	public void testExtractNull(){
		
		serverData = new ServerData(8080,  Arrays.asList((Object)null), rootContext, "url", 
				()->"context");
		serverData.extractResources();
		
	}
	
	@Test
	public void testExtractResourceClassName(){
		
		
		assertThat(serverData.extractResources().collect(Collectors.toList()).get(0).v1,is(ServletStatusResource.class.getName()));
		
	}
	@Test
	public void testExtractResourcePath(){
		
		
		assertThat(serverData.extractResources().collect(Collectors.toList()).get(0).v2,is("/servlet"));
		
	}

	
}
