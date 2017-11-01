package com.aol.micro.server.module;

import java.lang.annotation.Annotation;


import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;
import lombok.Getter;
@Getter
public class EmbeddedModule implements Module {

	private final SetX<Class<? extends Annotation>> restAnnotationClasses;
	private final SetX<Class<?>> restResourceClasses;
	private final String context;
	
	private EmbeddedModule(Iterable<Class<? extends Annotation>> restAnnotationClasses, String context){
		this.restAnnotationClasses = SetX.fromIterable(restAnnotationClasses);
		this.context = context;
		this.restResourceClasses = SetX.empty();
	}
	
	private EmbeddedModule(String context, Iterable<Class<?>> restTagClasses){
		this.context = context;
		this.restResourceClasses = SetX.fromIterable(restTagClasses);
		this.restAnnotationClasses = SetX.empty();
	}
	
	public static  EmbeddedModule annotationModule(Iterable<Class<? extends Annotation>> restAnnotationClasses, String context){
		return new EmbeddedModule(restAnnotationClasses, context);
	}
	public static  EmbeddedModule tagInterfaceModule(Iterable<Class<?>> restTagInterfaces, String context){
		return new EmbeddedModule( context,restTagInterfaces);
	}
}
