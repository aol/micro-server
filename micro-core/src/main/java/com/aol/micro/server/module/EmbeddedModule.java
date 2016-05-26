package com.aol.micro.server.module;

import java.lang.annotation.Annotation;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;

import lombok.Getter;
@Getter
public class EmbeddedModule implements Module {

	private final PSetX<Class<? extends Annotation>> restAnnotationClasses;
	private final PSetX<Class<?>> restResourceClasses;
	private final String context;
	
	private EmbeddedModule(Iterable<Class<? extends Annotation>> restAnnotationClasses, String context){
		this.restAnnotationClasses = PSetX.fromIterable(restAnnotationClasses);
		this.context = context;
		this.restResourceClasses = PSetX.empty();
	}
	
	private EmbeddedModule(String context, Iterable<Class<?>> restTagClasses){
		this.context = context;
		this.restResourceClasses = PSetX.fromIterable(restTagClasses);
		this.restAnnotationClasses = PSetX.empty();
	}
	
	public static  EmbeddedModule annotationModule(Iterable<Class<? extends Annotation>> restAnnotationClasses, String context){
		return new EmbeddedModule(restAnnotationClasses, context);
	}
	public static  EmbeddedModule tagInterfaceModule(Iterable<Class<?>> restTagInterfaces, String context){
		return new EmbeddedModule( context,restTagInterfaces);
	}
}
