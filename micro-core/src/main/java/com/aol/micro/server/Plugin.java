package com.aol.micro.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;
import javax.ws.rs.core.FeatureContext;

import com.aol.cyclops.data.collections.extensions.persistent.PMapX;
import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.micro.server.rest.RestConfiguration;
import com.aol.micro.server.servers.ServerApplicationFactory;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.spring.SpringBuilder;
import com.aol.micro.server.spring.SpringDBConfig;
import com.aol.micro.server.utility.HashMapBuilder;
import com.fasterxml.jackson.databind.Module;

/**
 * To implement a plugin for Microserver, implement this interface in your library and add the fully resolved class name to 
 * META-INF/services/com.aol.micro.server.Plugin
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
	default Function<FeatureContext, PMapX<String, Object>> jacksonFeatureProperties(){
		return context->PMapX.empty();
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
	default PSetX<Module> jacksonModules(){
		return PSetX.empty();
	}
	/**
	 * @return jax-rs Resources for this plugin
	 */
	default PSetX<Class> jaxRsResources(){
		return PSetX.empty();
	}
	/**
	 * @return  jax-rs Packages for this plugin
	 */
	default PSetX<String> jaxRsPackages(){
		return  PSetX.empty();
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
	default PSetX<Class> springClasses(){
		return PSetX.empty();
	}
	/**
	 * @return Servlet Context Listeners for this plugin
	 */
	default PSetX<Function<ServerData,ServletContextListener>> servletContextListeners(){
		return PSetX.empty();
	}
	/**
	 * @return Servlet Request Listeners for this plugin
	 */
	default PSetX<Function<ServerData,ServletRequestListener>> servletRequestListeners(){
		return PSetX.empty();
	}
	/**
	 * @return Filters for this plugin
	 */
	default Function<ServerData,PMapX<String,Filter>> filters(){
		return serverData -> PMapX.empty();
	}
	/**
	 * @return Servlets for this plugin
	 */
	default Function<ServerData,PMapX<String,Servlet>> servlets(){
		return serverData -> PMapX.empty();
	}
	/**
	 * @return jax-rs Providers for this plugin
	 */
	default PStackX<String> providers(){
		return PStackX.empty();
	}
	
	/**
	 * @return Jersey server properties for this plugin
	 */
	default PMapX<String, Object> getServerProperties() {		
		return PMapX.empty();	
	}
}
