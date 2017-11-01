package com.aol.micro.server.event.metrics;


import com.aol.micro.server.Plugin;
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
public class MetricEventsPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return SetX.of(
				Configuration.class,
				MetricsCatcher.class);
	}

	

}
