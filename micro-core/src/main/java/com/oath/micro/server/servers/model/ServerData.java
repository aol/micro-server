package com.aol.micro.server.servers.model;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

import com.oath.cyclops.types.persistent.PersistentList;
import cyclops.collections.immutable.LinkedListX;
import cyclops.data.Seq;
import cyclops.reactive.ReactiveSeq;
import lombok.Getter;
import lombok.Builder;

import cyclops.data.tuple.Tuple;
import cyclops.data.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.module.Module;

@Getter
@Builder
public class ServerData {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final int port;

	private final PersistentList<Object> resources;
	private final ApplicationContext rootContext;
	private final String baseUrlPattern;
	private final Module module;

	public ServerData(int port, PersistentList resources,
			ApplicationContext rootContext,
			String baseUrlPattern, Module module) {

		this.port = port;
		this.module = module;
		this.resources = resources==null ? Seq.of() :resources;
		this.rootContext = rootContext;
		this.baseUrlPattern = baseUrlPattern;
	}
    public ServerData(int port, List resources,
                      ApplicationContext rootContext,
                      String baseUrlPattern, Module module) {

        this.port = port;
        this.module = module;
        this.resources = resources==null ? LinkedListX.of() : LinkedListX.fromIterable(resources);
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
