package com.aol.micro.server.general.exception.mapper;

import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;

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
		Set<String> set = new HashSet<>();
		set.add("com.aol.micro.server.general.exception.mapper");
		return set;
	}

	@Override
	public Set<Class> springClasses() {
		Set<Class> set = new HashSet<>();
		set.add(MapOfExceptionsToErrorCodes.class);
		return set;
		
	}
	
	
	
	
	

}
