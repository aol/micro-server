package com.aol.micro.server.module;

import static com.aol.micro.server.utility.UsefulStaticMethods.concat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Setter;

import org.pcollections.ConsPStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.micro.server.auto.discovery.CommonRestResource;
import com.aol.simple.react.exceptions.ExceptionSoftener;

public class RestResourceTagBuilder {

	private final static ExceptionSoftener softener = ExceptionSoftener.singleton.factory.getInstance();
	private final static Logger logger = LoggerFactory.getLogger(RestResourceTagBuilder.class);
	
	@Setter
	private static List<Class> defaultTags= ConsPStack.singleton(CommonRestResource.class);
	
	public static List<Class> restResourceTags(String... classes){
		return ConsPStack.from(concat(Stream.of(classes).map(cl -> toClass(cl)).collect(Collectors.toList()),defaultTags));
	}
	public static List<Class> restResourceTags(Class... classes){
		return ConsPStack.from(concat(Stream.of(classes).collect(Collectors.toList()),defaultTags));
	}

	private static Class toClass(String cl) {
		try {
			return Class.forName(cl);
		} catch (ClassNotFoundException e) {
			logger.error("Class not found for {}", cl);
			softener.throwSoftenedException(e);
		}
		return null;
	}
}
