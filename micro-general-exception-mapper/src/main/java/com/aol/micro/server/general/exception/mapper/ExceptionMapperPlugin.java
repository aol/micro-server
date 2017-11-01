package com.aol.micro.server.general.exception.mapper;

import java.util.HashSet;
import java.util.Set;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class ExceptionMapperPlugin implements Plugin{

	@Override
	public Set<String> jaxRsPackages() {
		return SetX.of("com.aol.micro.server.general.exception.mapper");
		
	}

	@Override
	public Set<Class> springClasses() {
		return SetX.of(MapOfExceptionsToErrorCodes.class);
	}
	
	
	
	
	

}
