package com.oath.micro.server.guava;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.guava.spring.GuavaConfig;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import cyclops.reactive.collections.mutable.SetX;

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
public class GuavaPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return SetX.of(GuavaConfig.class);
	}
	@Override
	public Set<Module> jacksonModules(){
		return SetX.of(new GuavaModule());
	}

	

}
