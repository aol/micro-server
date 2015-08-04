package com.aol.micro.server.rest.jersey;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.Getter;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.servers.ServerThreadLocalVariables;

public class JerseyRestApplication extends ResourceConfig {

	@Getter
	private static volatile ConcurrentMap<String, List<Object>> resourcesMap = new ConcurrentHashMap<>();
	
	@Getter
	private static volatile ConcurrentMap<String, List<String>> packages = new ConcurrentHashMap<>();
	
	@Getter
	private static volatile ConcurrentMap<String, List<Class>> resourcesClasses = new ConcurrentHashMap<>();

	public JerseyRestApplication() {
		this(resourcesMap.get(ServerThreadLocalVariables.getContext().get()),
				packages.get(ServerThreadLocalVariables.getContext().get()),
				resourcesClasses.get(ServerThreadLocalVariables.getContext().get()));
		
	}

	public JerseyRestApplication(List<Object> allResources,List<String> packages, List<Class> resources) {

		
		if (allResources != null) {
			for (Object next : allResources) {
				if(isSingleton(next))
					register(next);
				else
					register(next.getClass());

			}
		}
		
		property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        //http://stackoverflow.com/questions/25755773/bean-validation-400-errors-are-returning-default-error-page-html-instead-of-re
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");

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
