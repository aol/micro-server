package com.aol.micro.server.rest.swagger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;
import cyclops.collections.immutable.PSetX;
import cyclops.function.Lambda;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class SwaggerPlugin implements Plugin{
	
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.empty();
	}

	@Override
	public PSetX<Function<ServerData,ServletContextListener>> servletContextListeners(){
		return PSetX.of(Lambda.l1(serverData -> new SwaggerInitializer(serverData)));
		
	}

	@Override
	public PSetX<Class<?>> jaxRsResources() {
		return PSetX.of(ApiListingResourceJSON.class,JerseyApiDeclarationProvider.class,
				JerseyResourceListingProvider.class);
	}

	@Override
	public PSetX<String> jaxRsPackages() {
		return PSetX.of("com.wordnik.swagger.sample.resource",
				"com.wordnik.swagger.sample.util"	);
	}
}
