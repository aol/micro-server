package com.aol.micro.server.rest;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;

import org.glassfish.jersey.server.ResourceConfig;

import com.google.common.collect.Maps;

public class RestApplication extends ResourceConfig {

	@Getter
	private static volatile ConcurrentMap<String, List<RestResource>> resourcesMap = Maps.newConcurrentMap();
	
	@Getter
	private static volatile ConcurrentMap<String, String> packages = Maps.newConcurrentMap();

	public RestApplication() {
		this(resourcesMap.get(Thread.currentThread().getName()));
	}

	public RestApplication(List<RestResource> allResources) {
		if (allResources != null) {
			for (RestResource next : allResources) {
				register(next);

			}
		}
		packages.entrySet().stream().forEach( e -> packages(e.getKey(),e.getValue()));
		

		register(JacksonFeature.class);

		/* Swagger is causing issues for our rest api so need to fix it later
		packages("com.wordnik.swagger.jaxrs.json").packages("com.wordnik.swagger.sample.resource").packages("com.wordnik.swagger.sample.util")
				.register(ApiListingResourceJSON.class).register(JerseyApiDeclarationProvider.class).register(JerseyResourceListingProvider.class);
				*/
	}

}
