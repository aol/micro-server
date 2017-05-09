package com.aol.micro.server.events.plugin;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.events.ConfigureActiveJobsAspect;
import com.aol.micro.server.rest.resources.ConfigureResources;
import cyclops.collections.immutable.PSetX;


/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class EventsPlugin implements Plugin{
	
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(
				ConfigureActiveJobsAspect.class,
				ConfigureResources.class);
	}

	

}
