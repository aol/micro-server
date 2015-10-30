package com.aol.micro.server.javaslang;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javaslang.jackson.datatype.JavaslangModule;

import com.aol.micro.server.Plugin;
import com.fasterxml.jackson.databind.Module;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class JavaslangPlugin implements Plugin{
	
	
	@Override
	public Set<Module> jacksonModules(){
		return new HashSet<>(Arrays.asList(new JavaslangModule()));
	}

	

}
