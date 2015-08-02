package com.aol.micro.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
public interface FunctionalModule {

	default Set<Class> jaxRsResources(){
		return new HashSet<>();
	}
	default Set<String> jaxRsPackages(){
		return new HashSet<>();
	}
	default Set<Class> springClasses(){
		return new HashSet<>();
	}
	default Set<Function<ServerData,ServletContextListener>> servletContextListeners(){
		return new HashSet<>();
	}
	default Set<Function<ServerData,ServletRequestListener>> servletRequestListeners(){
		return new HashSet<>();
	}
	default Function<ServerData,Map<String,Filter>> filters(){
		return serverData -> new HashMap<>();
	}
	default Function<ServerData,Map<String,Servlet>> servlets(){
		return serverData -> new HashMap<>();
	}
	default List<String> providers(){
		return new ArrayList<>();
	}
}
