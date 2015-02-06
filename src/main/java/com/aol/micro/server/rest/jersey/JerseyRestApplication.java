package com.aol.micro.server.rest.jersey;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;

import org.glassfish.jersey.server.ResourceConfig;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.google.common.collect.Maps;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;

public class JerseyRestApplication extends ResourceConfig {

	@Getter
	private static volatile ConcurrentMap<String, List<RestResource>> resourcesMap = Maps.newConcurrentMap();
	
	@Getter
	private static volatile ConcurrentMap<String, String> packages = Maps.newConcurrentMap();

	public JerseyRestApplication() {
		this(resourcesMap.get(ServerThreadLocalVariables.getContext().get()));
	}

	public JerseyRestApplication(List<RestResource> allResources) {
		if (allResources != null) {
			for (RestResource next : allResources) {
				register(next);

			}
		}
		packages.entrySet().stream().forEach( e -> packages(e.getKey(),e.getValue()));
		

		register(JacksonFeature.class);

//		packages("com.wordnik.swagger.jaxrs.json")
			packages("com.wordnik.swagger.sample.resource")
			.packages("com.wordnik.swagger.sample.util")
			.register(ApiListingResourceJSON.class)
			.register(JerseyApiDeclarationProvider.class)
			.register(JerseyResourceListingProvider.class);
				
	}

}
