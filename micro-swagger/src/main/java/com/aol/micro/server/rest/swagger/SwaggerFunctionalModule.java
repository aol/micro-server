package com.aol.micro.server.rest.swagger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;

import com.aol.micro.server.FunctionalModule;
import com.aol.micro.server.servers.model.ServerData;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class SwaggerFunctionalModule implements FunctionalModule{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<Class>();
	}

	@Override
	public Set<Function<ServerData,ServletContextListener>> servletContextListeners(){
		return new HashSet<Function<ServerData,ServletContextListener>>(){{
			add(serverData -> new SwaggerInitializer(serverData));
		}};
	}

	@Override
	public Set<Class> jaxRsResources() {
		return new HashSet<>(Arrays.asList(ApiListingResourceJSON.class,JerseyApiDeclarationProvider.class,
				JerseyResourceListingProvider.class));
	}

	@Override
	public Set<String> jaxRsPackages() {
		return new HashSet<>(Arrays.asList("com.wordnik.swagger.sample.resource",
				"com.wordnik.swagger.sample.util"	));
	}
}
