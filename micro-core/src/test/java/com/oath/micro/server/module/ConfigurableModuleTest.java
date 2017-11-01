package com.oath.micro.server.module;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;

import cyclops.collections.mutable.SetX;
import cyclops.collections.immutable.LinkedListX;
import cyclops.data.HashSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



import com.oath.micro.server.auto.discovery.CommonRestResource;
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.servers.model.ServerData;
import com.oath.micro.server.utility.HashMapBuilder;

public class ConfigurableModuleTest {

	ConfigurableModule module;
	ConfigurableModule unchanged;
	private String context;
	private List<Class<?>> defaultResources;
	private Map<String, Filter> filters;
	private String jaxWsRsApplication;
	private List<ServletContextListener> listeners;
	private List<ServletRequestListener> requestListeners;
	private String providers;
	private Set<Class<?>> resourceClasses;
	private Set<Class<? extends Annotation>> resourceAnnotationClasses;
	private Map<String, Servlet> servlets;
	private HashSet<Class<?>> springConfigurationClasses;
	private List<String> defaultJaxRsPackages;
	
	private Map<String, Object> serverProperties = HashMapBuilder.<String, Object>map(SERVER_PROPERTIES_KEY, 1).build();

	
	private static final String SERVER_PROPERTIES_KEY = "serverPropertiesKey";
	
	private Module m = () -> "module";
	Consumer<WebServerProvider<HttpServer>> serverConfigManager = server-> {};
	@Before
	public void setup(){
		
		defaultJaxRsPackages = Arrays.asList("hello world");
		context="context";
		defaultResources =  new ArrayList<>();
		filters =   HashMapBuilder.<String,Filter>map("/*1",new DummyQueryIPRetriever()).build();
		jaxWsRsApplication = "jaxRsApp2";
		listeners = m.getListeners(ServerData.builder().resources(LinkedListX.empty()).build());
		requestListeners = m.getRequestListeners(ServerData.builder().resources(LinkedListX.empty()).build());
		providers = "providers2";
		Module m = () -> "hello";
		resourceClasses = SetX.empty();
		resourceAnnotationClasses = SetX.of(Rest.class);
		servlets = new HashMap<>();
		springConfigurationClasses = HashSet.of(this.getClass());
		
		
		module = ConfigurableModule.builder()
									.serverConfigManager((Consumer)serverConfigManager )
									.defaultJaxRsPackages(defaultJaxRsPackages)
									.context(context)
									.defaultResources(defaultResources)
									.filters(filters)
									.jaxWsRsApplication(jaxWsRsApplication)
									.listeners(listeners)
									.requestListeners(requestListeners)
									.providers(providers)
									.restResourceClasses(resourceClasses)
									.servlets(servlets)
									.springConfigurationClasses(springConfigurationClasses.toSet())
									.serverProperties(serverProperties)
									.build();
		
		unchanged = ConfigurableModule.builder()
										.context("unchanged")
										.build();				
	}
	@Test
	public void resetAllIsFalse(){
		ConfigurableModule m = ConfigurableModule.builder().build();
		
		assertFalse(m.resetAll);
	}
	@Test
	public void defaultResourcesDefault(){
		Module m = ConfigurableModule.builder().build();
		
		assertThat(m.getDefaultResources().size(),equalTo(0));
	}
	@Test
	public void defaultResourcesNew(){
		Module m = ConfigurableModule.builder().defaultResources(Arrays.asList(this.getClass())).build();
		assertThat(m.getDefaultResources().size(),equalTo(1));
		
	}
	@Test
	public void testGetServerConfigManager() {
		assertThat(module.withResetAll(true).getServerConfigManager(),is(serverConfigManager));
	}
	@Test
	public void testGetServerConfigManagerNull() {
		try {
		module.withServerConfigManager(null)
							.getServerConfigManager().accept(null);
		}catch(Exception e){
			fail(e.getMessage());
		}
	}
	@Test
	public void testGetRestResourceClassesResetAll() {
		assertThat(module.withResetAll(true).getRestResourceClasses(),is(resourceClasses));
	}
	@Test
	public void testGetRestResourceClasses() {
		assertThat(module.getRestResourceClasses(),hasItem(CommonRestResource.class));
	}

	@Test
	public void testGetRestResourceClassesUnchanged() {
		assertThat(unchanged.getRestResourceClasses(),is(m.getRestResourceClasses()));
	}
	@Test
	public void testGetRestAnnotationClassesResetAll() {
		
		assertThat(module.withResetAll(true).getRestAnnotationClasses(),is(resourceAnnotationClasses));
	}
	@Test
	public void testGetRestAnnotationClasses() {
		assertThat(module.getRestAnnotationClasses(),hasItem(Rest.class));
	}

	@Test
	public void testGetRestAnnotationClassesUnchanged() {
		assertThat(unchanged.getRestAnnotationClasses(),is(m.getRestAnnotationClasses()));
	}

	@Test
	public void testGetDefaultResourcesReset() {
		assertThat(module.withResetAll(true).getDefaultResources(),is(this.defaultResources));
	}
	@Test
	public void testGetDefaultResources() {
		assertThat(module.getDefaultResources().size(),is(0));
	}
	@Test
	public void testGetDefaultJaxRsPackagesReset() {
		assertThat(module.withResetAll(true).getDefaultJaxRsPackages(),is(this.defaultJaxRsPackages));
	}
	@Test
	public void testGetDefaultJaxRsPackagesResources() {
		assertThat(module.getDefaultJaxRsPackages().size(),is(1));
	}
	@Test
	public void testGetDefaultResourcesUnchanged() {
		assertThat(unchanged.getDefaultResources(),is(m.getDefaultResources()));
	}

	@Test
	public void testGetListeners() {
		assertThat(module.getListeners(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				is(m.getListeners(ServerData.builder().resources(LinkedListX.of()).build()).size()*2)); //doubled
	}
	@Test
	public void testGetListenersReset() {
		assertThat(module.withResetAll(true).getListeners(ServerData.builder().resources(LinkedListX.of()).build()),is(this.listeners));
	}
	@Test
	public void testGetListenersUnchanged() {
		assertThat(unchanged.getListeners(ServerData.builder().resources(LinkedListX.empty()).build()).size() ,
				is(m.getListeners(ServerData.builder().resources(LinkedListX.empty()).build()).size()));
	}
	@Test
	public void testGetRequestListeners() {
		assertThat(module.getRequestListeners(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				is(m.getRequestListeners(ServerData.builder().resources(LinkedListX.of()).build()).size()*2)); //doubled
	}
	@Test
	public void testGetRequestListenersReset() {
		assertThat(module.withResetAll(true).getRequestListeners(ServerData.builder().resources(LinkedListX.of()).build()),is(this.requestListeners));
	}
	@Test
	public void testGetRequestListenersUnchanged() {
		assertThat(unchanged.getRequestListeners(ServerData.builder().resources(LinkedListX.of()).build()).size() ,
				is(m.getRequestListeners(ServerData.builder().resources(LinkedListX.of()).build()).size()));
	}

	@Test
	public void testGetFilters() {
			assertThat(module.getFilters(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				is(1  ));
	}
	@Test
	public void testGetFiltersReset() {
		assertThat(module.withResetAll(true).getFilters(ServerData.builder().resources(LinkedListX.of()).build()),is(this.filters));
	}
	
	@Test
	public void testGetFiltersUnchanged() {
		
		assertThat(unchanged.getFilters(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				equalTo(m.getFilters( ServerData.builder().resources(LinkedListX.of()).build() ).size()));
	}

	
	
	@Test
	public void testGetServlets() {
		assertThat(module.getServlets(ServerData.builder().resources(LinkedListX.of()).build()),is(this.servlets));
	}
	
	@Test
	public void testGetServletsUnchanged() {
		assertThat(unchanged.getServlets(ServerData.builder().resources(LinkedListX.of()).build()),is(m.getServlets(ServerData.builder().resources(LinkedListX.of()).build())));
	}

	@Test
	public void testGetJaxWsRsApplication() {
		assertThat(module.getJaxWsRsApplication(),is(this.jaxWsRsApplication));
	}

	@Test(expected=IncorrectJaxRsPluginsException.class)
	public void testGetJaxWsRsApplicationUnchanged() {
		unchanged.getJaxWsRsApplication();
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
	public void testGetSpringConfigurationClassesReset() {
		assertThat(module.withResetAll(true).getSpringConfigurationClasses().size(),is(this.springConfigurationClasses.size()));
	}
	@Test
	public void testGetSpringConfigurationClasses() {
		assertThat(module.getSpringConfigurationClasses(),hasItem(this.getClass()));
	}
	@Test
	public void testGetSpringConfigurationClassesUnchanged() {
		assertThat(unchanged.getSpringConfigurationClasses(),is(m.getSpringConfigurationClasses()));
	}

	

	@Test
	public void testWithResourceAnnotationClasses() {
		System.out.println(this.resourceAnnotationClasses);
		System.out.println(unchanged.withRestAnnotationClasses(this.resourceAnnotationClasses).getRestAnnotationClasses());
		assertThat(unchanged.withRestAnnotationClasses(this.resourceAnnotationClasses).getRestAnnotationClasses(),is(module.getRestAnnotationClasses()));
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
		assertThat(unchanged.withListeners(this.listeners).getListeners(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				is(module.getListeners(ServerData.builder().resources(LinkedListX.of()).build()).size()));
	}

	
	@Test
	public void testWithFilters() {
		assertThat(unchanged.withFilters(this.filters).getFilters(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				is(module.getFilters(ServerData.builder().resources(LinkedListX.of()).build()).size()));
	}

	@Test
	public void testWithServlets() {
		assertThat(unchanged.withServlets(this.servlets).getServlets(ServerData.builder().resources(LinkedListX.of()).build()).size(),
				is(m.getServlets(ServerData.builder().resources(LinkedListX.of()).build()).size()));

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
		assertThat(unchanged.withSpringConfigurationClasses(this.springConfigurationClasses.toSet()).getSpringConfigurationClasses(),is(module.getSpringConfigurationClasses()));

	}
	
	@Test
	public void testGetServerProperties() {
		Assert.assertEquals(1, module.getServerProperties().get(SERVER_PROPERTIES_KEY));
	}
	
	@Test
	public void testGetServerPropertiesResetAll() {
		Assert.assertThat(module.withResetAll(true).getServerProperties(), is(serverProperties));
	}

}
