package com.aol.micro.server.module;

import java.util.List;

import lombok.Getter;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
@Getter
public class EmbeddedModule implements Module {

	private final PStackX<Class> restAnnotationClasses;
	private final String context;
	
	public EmbeddedModule(List<Class> restAnnotationClasses, String context){
		this.restAnnotationClasses = PStackX.fromCollection(restAnnotationClasses);
		this.context = context;
	}
	
}
