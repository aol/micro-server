package com.aol.micro.server.module;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.ImmutableList;

public class ConfigurableModuleTest {

	ConfigurableModule module;
	ConfigurableModule unchanged;
	private String context;
	private List<Class> defaultResources;
	private Map<String, Filter> filters;
	private String jaxWsRsApplication;
	private List<ServletContextListener> listeners;
	private String providers;
	private List<Class> resourceClasses;
	private Map<String, Servlet> servlets;
	private Set<Class> springConfigurationClasses;
	
	private Module m = () -> "module";
	@Before
	public void setup(){
		
		context="context";
		defaultResources = mock(List.class);
		filters = mock(Map.class);
		jaxWsRsApplication = "jaxRsApp2";
		listeners = mock(List.class);
		providers = "providers2";
		resourceClasses = mock(List.class);
		servlets = mock(Map.class);
		springConfigurationClasses = mock(Set.class);
		
		module = ConfigurableModule.builder()
									.context(context)
									.defaultResources(defaultResources)
									.filters(filters)
									.jaxWsRsApplication(jaxWsRsApplication)
									.listeners(listeners)
									.providers(providers)
									.restResourceClasses(resourceClasses)
									.servlets(servlets)
									.springConfigurationClasses(springConfigurationClasses)
									.build();
		
		unchanged = ConfigurableModule.builder()
										.context("unchanged")
										.build();				
	}
	@Test
	public void testGetRestResourceClasses() {
		assertThat(module.getRestResourceClasses(),is(resourceClasses));
	}

	@Test
	public void testGetRestResourceClassesUnchanged() {
		assertThat(unchanged.getRestResourceClasses(),is(m.getRestResourceClasses()));
	}

	@Test
	public void testGetDefaultResources() {
		assertThat(module.getDefaultResources(),is(this.defaultResources));
	}
	@Test
	public void testGetDefaultResourcesUnchanged() {
		assertThat(unchanged.getDefaultResources(),is(m.getDefaultResources()));
	}

	@Test
	public void testGetListeners() {
		assertThat(module.getListeners(ServerData.builder().resources(ImmutableList.of()).build()),is(this.listeners));
	}
	@Test
	public void testGetListenersUnchanged() {
		assertThat(unchanged.getListeners(ServerData.builder().resources(ImmutableList.of()).build()).size() ,
				is(m.getListeners(ServerData.builder().resources(ImmutableList.of()).build()).size()));
	}

	@Test
	public void testGetFilters() {
		assertThat(module.getFilters(ServerData.builder().resources(ImmutableList.of()).build()),is(this.filters));
	}
	@Test
	public void testGetFiltersUnchanged() {
		assertThat(unchanged.getFilters(ServerData.builder().resources(ImmutableList.of()).build()).get("/*"),
				is(m.getFilters( ServerData.builder().resources(ImmutableList.of()).build() ).get("/*").getClass()));
	}

	
	
	@Test
	public void testGetServlets() {
		assertThat(module.getServlets(ServerData.builder().resources(ImmutableList.of()).build()),is(this.servlets));
	}
	
	@Test
	public void testGetServletsUnchanged() {
		assertThat(unchanged.getServlets(ServerData.builder().resources(ImmutableList.of()).build()),is(m.getServlets(ServerData.builder().resources(ImmutableList.of()).build())));
	}

	@Test
	public void testGetJaxWsRsApplication() {
		assertThat(module.getJaxWsRsApplication(),is(this.jaxWsRsApplication));
	}

	@Test
	public void testGetJaxWsRsApplicationUnchanged() {
		assertThat(unchanged.getJaxWsRsApplication(),is(m.getJaxWsRsApplication()));
	}

	@Test
	public void testGetProviders() {
		assertThat(module.getProviders(),is(this.providers));
	}
	@Test
	public void testGetProvidersUnchanged() {
		assertThat(unchanged.getProviders(),is(m.getProviders()));
	}

	@Test
	public void testGetContext() {
		assertThat(module.getContext(),is(this.context));
	}

	@Test
	public void testGetSpringConfigurationClasses() {
		assertThat(module.getSpringConfigurationClasses(),is(this.springConfigurationClasses));
	}
	@Test
	public void testGetSpringConfigurationClassesUnchanged() {
		assertThat(unchanged.getSpringConfigurationClasses(),is(m.getSpringConfigurationClasses()));
	}

	

	

	@Test
	public void testWithResourceClasses() {
		assertThat(unchanged.withRestResourceClasses(this.resourceClasses).getRestResourceClasses(),is(module.getRestResourceClasses()));
	}

	@Test
	public void testWithDefaultResources() {
		assertThat(unchanged.withDefaultResources(this.defaultResources).getDefaultResources(),is(module.getDefaultResources()));
	}

	@Test
	public void testWithListeners() {
		assertThat(unchanged.withListeners(this.listeners).getListeners(ServerData.builder().resources(ImmutableList.of()).build()),is(module.getListeners(ServerData.builder().resources(ImmutableList.of()).build())));
	}

	@Test
	public void testWithFilters() {
		assertThat(unchanged.withFilters(this.filters).getFilters(ServerData.builder().resources(ImmutableList.of()).build()),is(module.getFilters(ServerData.builder().resources(ImmutableList.of()).build())));
	}

	@Test
	public void testWithServlets() {
		assertThat(unchanged.withServlets(this.servlets).getServlets(ServerData.builder().resources(ImmutableList.of()).build()),
				is(m.getServlets(ServerData.builder().resources(ImmutableList.of()).build())));

	}

	@Test
	public void testWithJaxWsRsApplication() {
		assertThat(unchanged.withJaxWsRsApplication(this.jaxWsRsApplication).getJaxWsRsApplication(),is(module.getJaxWsRsApplication()));
	}

	@Test
	public void testWithProviders() {
		assertThat(unchanged.withProviders(this.providers).getProviders(),is(module.getProviders()));

	}

	@Test
	public void testWithContext() {
		assertThat(unchanged.withContext(this.context).getContext(),is(module.getContext()));

	}

	@Test
	public void testWithSpringConfigurationClasses() {
		assertThat(unchanged.withSpringConfigurationClasses(this.springConfigurationClasses).getSpringConfigurationClasses(),is(module.getSpringConfigurationClasses()));

	}

}
