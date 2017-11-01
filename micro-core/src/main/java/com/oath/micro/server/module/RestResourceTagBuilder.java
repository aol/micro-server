package com.oath.micro.server.module;

import static com.oath.micro.server.utility.UsefulStaticMethods.concat;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.oath.cyclops.util.ExceptionSoftener;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.oath.micro.server.auto.discovery.CommonRestResource;

import lombok.Setter;


public class RestResourceTagBuilder {

	private final static Logger logger = LoggerFactory.getLogger(RestResourceTagBuilder.class);
	
	@Setter
	private static SetX<Class<?>> defaultTags= SetX.of(CommonRestResource.class);
	
	public static SetX<Class<?>> restResourceTags(String... classes){
		return (SetX)SetX.fromIterable(concat(Stream.of(classes).map(cl -> toClass(cl)).collect(Collectors.toList()),defaultTags));
	}
	public static SetX<Class<?>> restResourceTags(Class... classes){
		return (SetX)SetX.fromIterable(concat((List)Stream.of(classes).collect(Collectors.toList()),defaultTags));
	}
	public static SetX<Class<? extends Annotation>> restAnnotations(String... classes){
		return (SetX)SetX.fromIterable(concat(Stream.of(classes).map(cl -> toClass(cl)).collect(Collectors.toList()),defaultTags));
	}
	public static SetX<Class<? extends Annotation>> restAnnotations(Class<? extends Annotation>... classes){
		return (SetX)SetX.fromIterable(concat(Stream.of(classes).collect(Collectors.toList()),defaultTags));
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
