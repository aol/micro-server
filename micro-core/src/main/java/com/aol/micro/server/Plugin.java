package com.aol.micro.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.aol.micro.server.rest.RestConfiguration;
import com.aol.micro.server.servers.ServerApplicationFactory;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.spring.SpringApplicationConfigurator;
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
	default Function<FeatureContext, Map<String, Object>> jacksonFeatureProperties(){
		return context->HashMapBuilder.of();
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
		return new HashSet<>();
	}
	/**
	 * @return jax-rs Resources for this plugin
	 */
	default Set<Class> jaxRsResources(){
		return new HashSet<>();
	}
	/**
	 * @return  jax-rs Packages for this plugin
	 */
	default Set<String> jaxRsPackages(){
		return new HashSet<>();
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
		return new HashSet<>();
	}
	/**
	 * @return Servlet Context Listeners for this plugin
	 */
	default Set<Function<ServerData,ServletContextListener>> servletContextListeners(){
		return new HashSet<>();
	}
	/**
	 * @return Servlet Request Listeners for this plugin
	 */
	default Set<Function<ServerData,ServletRequestListener>> servletRequestListeners(){
		return new HashSet<>();
	}
	/**
	 * @return Filters for this plugin
	 */
	default Function<ServerData,Map<String,Filter>> filters(){
		return serverData -> new HashMap<>();
	}
	/**
	 * @return Servlets for this plugin
	 */
	default Function<ServerData,Map<String,Servlet>> servlets(){
		return serverData -> new HashMap<>();
	}
	/**
	 * @return jax-rs Providers for this plugin
	 */
	default List<String> providers(){
		return new ArrayList<>();
	}
}
