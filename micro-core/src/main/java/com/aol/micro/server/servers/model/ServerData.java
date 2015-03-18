package com.aol.micro.server.servers.model;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.module.Module;
import com.google.common.collect.ImmutableList;

@Getter
@Builder
public class ServerData {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final int port;

	private final ImmutableList<Object> resources;
	private final ApplicationContext rootContext;
	private final String baseUrlPattern;
	private final Module module;

	public ServerData(int port, List resources, 
			ApplicationContext rootContext,
			String baseUrlPattern, Module module) {

		this.port = port;
		this.module = module;
		this.resources = ImmutableList.copyOf(resources);
		this.rootContext = rootContext;
		this.baseUrlPattern = baseUrlPattern;
	}

	public void logResources() {
		
		logger.info("Configured resource classes :-");
		resources.stream().forEach(resource -> logger.info(resource.getClass().getName()));
		

	}

	
}
