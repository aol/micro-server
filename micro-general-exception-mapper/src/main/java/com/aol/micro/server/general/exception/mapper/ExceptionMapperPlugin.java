package com.aol.micro.server.general.exception.mapper;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

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
	public PersistentSetX<String> jaxRsPackages() {
		return PersistentSetX.of("com.aol.micro.server.general.exception.mapper");
		
	}

	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(MapOfExceptionsToErrorCodes.class);
	}

}
