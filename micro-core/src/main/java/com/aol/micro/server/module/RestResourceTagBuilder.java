package com.aol.micro.server.module;

import static com.aol.micro.server.utility.UsefulStaticMethods.concat;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aol.cyclops2.util.ExceptionSoftener;
import cyclops.collections.immutable.PersistentSetX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.aol.micro.server.auto.discovery.CommonRestResource;

import lombok.Setter;


public class RestResourceTagBuilder {

	private final static Logger logger = LoggerFactory.getLogger(RestResourceTagBuilder.class);
	
	@Setter
	private static PersistentSetX<Class<?>> defaultTags= PersistentSetX.of(CommonRestResource.class);
	
	public static PersistentSetX<Class<?>> restResourceTags(String... classes){
		return (PersistentSetX)PersistentSetX.fromIterable(concat(Stream.of(classes).map(cl -> toClass(cl)).collect(Collectors.toList()),defaultTags));
	}
	public static PersistentSetX<Class<?>> restResourceTags(Class... classes){
		return (PersistentSetX)PersistentSetX.fromIterable(concat((List)Stream.of(classes).collect(Collectors.toList()),defaultTags));
	}
	public static PersistentSetX<Class<? extends Annotation>> restAnnotations(String... classes){
		return (PersistentSetX)PersistentSetX.fromIterable(concat(Stream.of(classes).map(cl -> toClass(cl)).collect(Collectors.toList()),defaultTags));
	}
	public static PersistentSetX<Class<? extends Annotation>> restAnnotations(Class<? extends Annotation>... classes){
		return (PersistentSetX)PersistentSetX.fromIterable(concat(Stream.of(classes).collect(Collectors.toList()),defaultTags));
	}

	private static Class<?> toClass(String cl) {
		try {
			
			return Class.forName(cl,true,RestResourceTagBuilder.class.getClassLoader());
		} catch (ClassNotFoundException e) {
			logger.error("Class not found for {}", cl);
			ExceptionSoftener.throwSoftenedException(e);
		}
		return null;
	}
}
