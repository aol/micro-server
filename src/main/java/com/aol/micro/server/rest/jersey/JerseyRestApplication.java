package com.aol.micro.server.rest.jersey;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;

import org.glassfish.jersey.server.ResourceConfig;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.rest.resources.ActiveResource;
import com.aol.micro.server.rest.resources.ManifestResource;
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
		//clean this up!!
		List<Class> singletons =Arrays.asList(ActiveResource.class, ManifestResource.class);
		if (allResources != null) {
			for (Object next : allResources) {
				if(isSingleton(next))
					register(next);
				else
					register(next.getClass());

			}
		}
		packages.stream().forEach( e -> packages(e));
		resources.stream().forEach( e -> register(e));

	}

	private boolean isSingleton(Object next) {
		if(next instanceof RestResource)
			return ((RestResource)next).isSingleton();
		Rest rest = next.getClass().getAnnotation(Rest.class);
		if(rest == null)
			return false;
		return rest.isSingleton();
	}

	public static void clear() {
		resourcesMap.clear();
		packages.clear();
		
	}

}
