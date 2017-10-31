package com.aol.micro.server.events.plugin;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.events.ConfigureActiveJobsAspect;
import com.aol.micro.server.rest.resources.ConfigureResources;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;


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
	public Set<Class> springClasses() {
		return SetX.of(
				ConfigureActiveJobsAspect.class,
				ConfigureResources.class);
	}

	

}
