package com.aol.micro.server.rest.jersey;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;

import org.glassfish.jersey.server.ResourceConfig;

import com.aol.micro.server.servers.ServerThreadLocalVariables;
import com.google.common.collect.Maps;

public class JerseyRestApplication extends ResourceConfig {

	@Getter
	private static volatile ConcurrentMap<String, List<Object>> resourcesMap = Maps.newConcurrentMap();
	
	@Getter
	private static volatile ConcurrentMap<String, List<String>> packages = Maps.newConcurrentMap();
	
	@Getter
	private static volatile ConcurrentMap<String, List<Class>> resourcesClasses = Maps.newConcurrentMap();

	public JerseyRestApplication() {
		this(resourcesMap.get(ServerThreadLocalVariables.getContext().get()),
				packages.get(ServerThreadLocalVariables.getContext().get()),
				resourcesClasses.get(ServerThreadLocalVariables.getContext().get()));
		
	}

	public JerseyRestApplication(List<Object> allResources,List<String> packages, List<Class> resources) {
		if (allResources != null) {
			for (Object next : allResources) {
				register(next);

			}
		}
		packages.stream().forEach( e -> packages(e));
		resources.stream().forEach( e -> register(e));
/**
		register(JacksonFeature.class);


			packages("com.wordnik.swagger.sample.resource")
			.packages("com.wordnik.swagger.sample.util")
			.register(ApiListingResourceJSON.class)
			.register(JerseyApiDeclarationProvider.class)
			.register(JerseyResourceListingProvider.class);
				**/
	}

	public static void clear() {
		resourcesMap.clear();
		packages.clear();
		
	}

}
