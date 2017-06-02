package com.aol.micro.server.module;

import java.lang.annotation.Annotation;


import cyclops.collections.immutable.PersistentSetX;
import lombok.Getter;
@Getter
public class EmbeddedModule implements Module {

	private final PersistentSetX<Class<? extends Annotation>> restAnnotationClasses;
	private final PersistentSetX<Class<?>> restResourceClasses;
	private final String context;
	
	private EmbeddedModule(Iterable<Class<? extends Annotation>> restAnnotationClasses, String context){
		this.restAnnotationClasses = PersistentSetX.fromIterable(restAnnotationClasses);
		this.context = context;
		this.restResourceClasses = PersistentSetX.empty();
	}
	
	private EmbeddedModule(String context, Iterable<Class<?>> restTagClasses){
		this.context = context;
		this.restResourceClasses = PersistentSetX.fromIterable(restTagClasses);
		this.restAnnotationClasses = PersistentSetX.empty();
	}
	
	public static  EmbeddedModule annotationModule(Iterable<Class<? extends Annotation>> restAnnotationClasses, String context){
		return new EmbeddedModule(restAnnotationClasses, context);
	}
	public static  EmbeddedModule tagInterfaceModule(Iterable<Class<?>> restTagInterfaces, String context){
		return new EmbeddedModule( context,restTagInterfaces);
	}
}
