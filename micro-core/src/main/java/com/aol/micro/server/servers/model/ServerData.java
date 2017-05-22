package com.aol.micro.server.servers.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import cyclops.collections.immutable.PStackX;
import cyclops.stream.ReactiveSeq;
import lombok.Getter;
import lombok.Builder;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.module.Module;

@Getter
@Builder
public class ServerData {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final int port;

	private final PStackX<Object> resources;
	private final ApplicationContext rootContext;
	private final String baseUrlPattern;
	private final Module module;

	public ServerData(int port, List resources,
			ApplicationContext rootContext,
			String baseUrlPattern, Module module) {

		this.port = port;
		this.module = module;
		this.resources = resources==null ? PStackX.of() : PStackX.fromCollection(resources);
		this.rootContext = rootContext;
		this.baseUrlPattern = baseUrlPattern;
	}

	public ReactiveSeq<Tuple2<String,String>> extractResources() {


		return resources.stream().peek(resource -> logMissingPath(resource))
								.filter(resource-> resource.getClass().getAnnotation(Path.class)!=null)
								.map(resource -> Tuple.tuple(resource.getClass().getName(),
										resource.getClass().getAnnotation(Path.class).value()));


	}

	private void logMissingPath(Object resource) {
		if(resource.getClass().getAnnotation(Path.class)==null){
			logger.info("Resource with no path  " + resource);

		}
	}


}
