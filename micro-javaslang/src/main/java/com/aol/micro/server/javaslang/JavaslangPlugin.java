package com.aol.micro.server.javaslang;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.fasterxml.jackson.databind.Module;

import javaslang.jackson.datatype.JavaslangModule;

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
	public PSetX<Module> jacksonModules(){
		return PSetX.of(new JavaslangModule());
	}

	

}
