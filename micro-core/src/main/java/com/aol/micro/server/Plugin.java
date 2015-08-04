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




import com.aol.micro.server.servers.model.ServerData;

/**
 * To implement a plugin for Microserver, implement this interface in your library and add the fully resolved class name to 
 * META-INF/services/com.aol.micro.server.FunctionalModule
 * 
 * in your library
 * 
 * @author johnmcclean
 *
 */
public interface Plugin {

	default Optional<ServerApplicationFactory> serverApplicationFactory(){
		return Optional.empty();
	}
	default Set<com.fasterxml.jackson.databind.Module> jacksonModules(){
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
