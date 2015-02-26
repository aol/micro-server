package com.aol.micro.server.servers.model;

import java.util.List;

import lombok.Getter;
import lombok.experimental.Builder;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.module.Module;
import com.google.common.collect.ImmutableList;

@Getter
@Builder
public class ServerData {

	private final int port;

	private final ImmutableList<Object> resources;
	private final ConfigurableApplicationContext rootContext;
	private final String baseUrlPattern;
	private final Module module;

	public ServerData(int port, List resources, 
			ConfigurableApplicationContext rootContext,
			String baseUrlPattern, Module module) {

		this.port = port;
		this.module = module;
		this.resources = ImmutableList.copyOf(resources);
		this.rootContext = rootContext;
		this.baseUrlPattern = baseUrlPattern;
	}

	
}
