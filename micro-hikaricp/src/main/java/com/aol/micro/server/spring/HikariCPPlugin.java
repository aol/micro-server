package com.aol.micro.server.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.spring.datasource.HikariCPConfig;
import com.aol.micro.server.spring.datasource.HikariCPDataSourceBuilder;

/**
 * 
 * @author kewang
 *
 */
public class HikariCPPlugin implements Plugin {

	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(HikariCPConfig.class, HikariCPDataSourceBuilder.class));
	}

	@Override
	public Set<Function<ServerData, ServletContextListener>> servletContextListeners() {
		return null;
	}

	@Override
	public Set<Class> jaxRsResources() {
		return null;
	}

	@Override
	public Set<String> jaxRsPackages() {
		return null;
	}

}
