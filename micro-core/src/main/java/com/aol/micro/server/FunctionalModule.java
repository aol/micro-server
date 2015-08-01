package com.aol.micro.server;

import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;

import com.aol.micro.server.servers.model.ServerData;

public interface FunctionalModule {

	public Set<Class> jaxRsResources();
	public Set<String> jaxRsPackages();
	public Set<Class> springClasses();
	public Set<Function<ServerData,ServletContextListener>> servletContextListeners();
}
