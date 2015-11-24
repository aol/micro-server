package com.aol.micro.server.servers.model;

import java.util.List;
import java.util.stream.Stream;

import javax.ws.rs.Path;

import lombok.Getter;
import lombok.experimental.Builder;

import org.pcollections.ConsPStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.aol.cyclops.lambda.tuple.PTuple2;
import com.aol.cyclops.lambda.tuple.PowerTuples;
import com.aol.micro.server.module.Module;

@Getter
@Builder
public class ServerData {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final int port;

	private final List<Object> resources;
	private final ApplicationContext rootContext;
	private final String baseUrlPattern;
	private final Module module;

	public ServerData(int port, List resources, 
			ApplicationContext rootContext,
			String baseUrlPattern, Module module) {

		this.port = port;
		this.module = module;
		this.resources = ConsPStack.from(resources);
		this.rootContext = rootContext;
		this.baseUrlPattern = baseUrlPattern;
	}

	public Stream<PTuple2<String,String>> extractResources() {
		
		
		return resources.stream().peek(resource -> logMissingPath(resource)).map(resource -> PowerTuples.tuple(resource.getClass().getName(), 
										resource.getClass().getAnnotation(Path.class).value()));
		

	}

	private void logMissingPath(Object resource) {
		if(resource.getClass().getAnnotation(Path.class)==null){
			logger.error("Missing path attribute for resource " + resource);
			throw new RestResourceMissingPathException("Missing path attribute for resource " + resource);
		}
	}

	
}
