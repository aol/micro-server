package com.oath.micro.server;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;
import javax.ws.rs.core.FeatureContext;


import com.oath.micro.server.rest.RestConfiguration;
import com.oath.micro.server.servers.ServerApplicationFactory;
import com.oath.micro.server.servers.model.ServerData;
import com.oath.micro.server.spring.SpringBuilder;
import com.oath.micro.server.spring.SpringDBConfig;
import com.fasterxml.jackson.databind.Module;
import cyclops.collections.mutable.ListX;
import cyclops.collections.mutable.MapX;
import cyclops.collections.mutable.SetX;

/**
 * To implement a plugin for Microserver, implement this interface in your library and add the fully resolved class name to 
 * META-INF/services/com.oath.micro.server.Plugin
 * 
 * in your library
 * 
 * @author johnmcclean
 *
 */
public interface Plugin {

	/**
	 * @return Engine for building Spring Context
	 */
	default SpringBuilder springBuilder(){
		return null;
	}
	/**
	 * @return Configuration for the jax-rs servlet
	 */
	default Optional<RestConfiguration> restServletConfiguration(){
		return Optional.empty();
	}
	/**
	 * @return Jackson feature properties
	 */
	default Function<FeatureContext, Map<String, Object>> jacksonFeatureProperties(){
		return context-> MapX.empty();
	}
	/**
	 * @return jax-rs Application name
	 */
	default  Optional<String> jaxWsRsApplication(){
		return Optional.empty();
	}
	/**
	 * @return Factory for creating web server instances
	 */
	default Optional<ServerApplicationFactory> serverApplicationFactory(){
		return Optional.empty();
	}
	
	/**
	 * @return Jackson modules for this plugin
	 */
	default Set<Module> jacksonModules(){
		return SetX.empty();
	}
	/**
	 *  @return jax-rs Resources (Objects) for this plugin
	 */
	default Set<Object> jaxRsResourceObjects(){
		return SetX.empty();
	}
	/**
	 * @return jax-rs Resources (Classes) for this plugin
	 */
	default Set<Class<?>> jaxRsResources(){
		return SetX.empty();
	}
	/**
	 * @return  jax-rs Packages for this plugin
	 */
	default Set<String> jaxRsPackages(){
		return  SetX.empty();
	}
	/**
	 * @return Used for configuring Data Beans (or other Beans) directly into the ApplicationContext
	 */
	default Optional<SpringDBConfig> springDbConfigurer(){
		return Optional.empty();
	}
	/**
	 * @return Spring configuration classes for this plugin
	 */
	default Set<Class> springClasses(){
		return SetX.empty();
	}
	/**
	 * @return Servlet Context Listeners for this plugin
	 */
	default Set<Function<ServerData,ServletContextListener>> servletContextListeners(){
		return SetX.empty();
	}
	/**
	 * @return Servlet Request Listeners for this plugin
	 */
	default Set<Function<ServerData,ServletRequestListener>> servletRequestListeners(){
		return SetX.empty();
	}
	/**
	 * @return Filters for this plugin
	 */
	default Function<ServerData,Map<String,Filter>> filters(){
		return serverData -> MapX.empty();
	}
	/**
	 * @return Servlets for this plugin
	 */
	default Function<ServerData,Map<String,Servlet>> servlets(){
		return serverData -> MapX.empty();
	}
	/**
	 * @return jax-rs Providers for this plugin
	 */
	default List<String> providers(){
		return ListX.empty();
	}
	
	/**
	 * @return Jersey server properties for this plugin
	 */
	default Map<String, Object> getServerProperties() {		
		return MapX.empty();
	}
}
