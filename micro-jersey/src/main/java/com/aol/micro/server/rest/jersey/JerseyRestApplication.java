package com.aol.micro.server.rest.jersey;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import lombok.Getter;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.module.JaxRsProvider;
import com.aol.micro.server.servers.ServerThreadLocalVariables;

public class JerseyRestApplication extends ResourceConfig {

	@Getter
	private static final ConcurrentMap<String, List<Object>> resourcesMap = new ConcurrentHashMap<>();
	
	@Getter
	private static  final  ConcurrentMap<String, List<String>> packages = new ConcurrentHashMap<>();
	
	@Getter
	private static final  ConcurrentMap<String, List<Class<?>>> resourcesClasses = new ConcurrentHashMap<>();
	
	@Getter
	private static final  ConcurrentMap<String, Consumer<JaxRsProvider<Object>>> resourceConfigManager = new ConcurrentHashMap<>();
	
	@Getter
	private static final  ConcurrentMap<String, Map<String, Object>> serverPropertyMap = new ConcurrentHashMap<>();

	public JerseyRestApplication() {
		this(resourcesMap.get(ServerThreadLocalVariables.getContext().get()),
				packages.get(ServerThreadLocalVariables.getContext().get()),
				resourcesClasses.get(ServerThreadLocalVariables.getContext().get()),
		        serverPropertyMap.get(ServerThreadLocalVariables.getContext().get()));		
	}

	public JerseyRestApplication(List<Object> allResources,List<String> packages, List<Class<?>> resources, Map<String, Object> serverProperties) {
		
		if (allResources != null) {
			for (Object next : allResources) {
				if(isSingleton(next))
					register(next);
				else
					register(next.getClass());
			}
		}
		
		if (serverProperties.isEmpty()) {
			property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
	        //http://stackoverflow.com/questions/25755773/bean-validation-400-errors-are-returning-default-error-page-html-instead-of-re
	        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, "true");
		} else {
			for (Map.Entry<String, Object> entry : serverProperties.entrySet()) {
				property(entry.getKey(), entry.getValue());
			}
		}

        packages.stream().forEach( e -> packages(e));
		resources.stream().forEach( e -> register(e));
		
		Optional.ofNullable(this.resourceConfigManager.get(ServerThreadLocalVariables.getContext().get())).ifPresent(e->e.accept(new JaxRsProvider<>(this)));

	}

	private boolean isSingleton(Object next) {
		if(next instanceof RestResource)
			return ((RestResource)next).isSingleton();
		Rest rest = next.getClass().getAnnotation(Rest.class);
		if(rest == null)
			return !(next instanceof Class);
		return rest.isSingleton();
	}

	public static void clear() {
		resourcesMap.clear();
		packages.clear();
		
	}

}
