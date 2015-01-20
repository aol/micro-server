package com.aol.micro.server.servers;

import java.util.stream.Collectors;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.Module;
import com.aol.micro.server.rest.RestResource;
import com.google.common.collect.ImmutableList;

public class SpringApplicationCreator {
	
	public static AnnotationConfigWebApplicationContext createSpringApp(Class...classes)  {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setAllowCircularReferences(false);
		rootContext.register(classes);
		rootContext.refresh();
		return rootContext;
	}
	
	public static ImmutableList<RestResource> createJerseyApp(AnnotationConfigWebApplicationContext rootContext,Module type){
		return ImmutableList.copyOf(rootContext.getBeansOfType(RestResource.class).values().stream()
				.filter((next) ->  next.getModule().orElse(type).equals(type) ).collect(Collectors.toSet()));
	}
}
