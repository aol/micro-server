package com.aol.micro.server.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import app.com.aol.micro.server.StatusResource;

import com.aol.micro.server.module.Module;
import com.aol.micro.server.rest.swagger.SwaggerInitializer;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;

public class ServerDataTest {

	private ServerData serverData;
	private AnnotationConfigWebApplicationContext rootContext;

	@Before
	public void setUp() {
		rootContext = mock(AnnotationConfigWebApplicationContext.class);
		serverData = new ServerData(8080, null, null, Lists.newArrayList(new StatusResource()), rootContext, "url", 
				()->"context");
	}

	@Test
	public void testGetters() {
		assertThat(serverData.getBaseUrlPattern(), is("url"));
		assertThat(serverData.getFilterDataList().size(), is(0));
		assertThat(serverData.getResources().size(), is(1));
		assertThat(serverData.getRootContext(), is(rootContext));
		assertThat(serverData.getModule().getContext(), is("context"));
		assertThat(serverData.getPort(), is(8080));
	}

	@Test
	public void testInit() {

		SwaggerInitializer swaggerInitializer = mock(SwaggerInitializer.class);
		when(rootContext.getBean(SwaggerInitializer.class)).thenReturn(swaggerInitializer);
		serverData.init();
		verify(swaggerInitializer, times(1)).setServerData(serverData);

	}
}
