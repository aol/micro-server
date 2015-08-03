package com.aol.micro.server.events;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.FunctionalModule;
import com.aol.micro.server.rest.resources.ConfigureResources;


/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class EventsFunctionalModule implements FunctionalModule{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(
				ConfigureActiveJobsAspect.class,
				ConfigureResources.class));
	}

	

}
