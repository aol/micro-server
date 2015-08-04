package com.aol.micro.server.guava;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.guava.spring.GuavaConfig;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

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
		return new HashSet<>(Arrays.asList(GuavaConfig.class));
	}
	@Override
	public Set<Module> jacksonModules(){
		return new HashSet<>(Arrays.asList(new GuavaModule()));
	}

	

}
