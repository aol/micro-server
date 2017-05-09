package com.aol.micro.server.guava;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.guava.spring.GuavaConfig;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
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
public class GuavaPlugin implements Plugin{
	
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(GuavaConfig.class);
	}
	@Override
	public PSetX<Module> jacksonModules(){
		return PSetX.of(new GuavaModule());
	}

	

}
