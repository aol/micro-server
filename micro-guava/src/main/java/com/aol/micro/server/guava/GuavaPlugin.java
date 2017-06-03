package com.aol.micro.server.guava;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.guava.spring.GuavaConfig;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
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
public class GuavaPlugin implements Plugin{
	
	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(GuavaConfig.class);
	}
	@Override
	public PersistentSetX<Module> jacksonModules(){
		return PersistentSetX.of(new GuavaModule());
	}

	

}
