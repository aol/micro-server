package com.aol.micro.server.module;

import static com.aol.micro.server.utility.UsefulStaticMethods.concat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Setter;

import org.pcollections.ConsPStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.cyclops.util.ExceptionSoftener;
import com.aol.micro.server.auto.discovery.CommonRestResource;


public class RestResourceTagBuilder {

	private final static Logger logger = LoggerFactory.getLogger(RestResourceTagBuilder.class);
	
	@Setter
	private static PStackX<Class> defaultTags= PStackX.of(CommonRestResource.class);
	
	public static PStackX<Class> restResourceTags(String... classes){
		return PStackX.fromCollection(concat(Stream.of(classes).map(cl -> toClass(cl)).collect(Collectors.toList()),defaultTags));
	}
	public static PStackX<Class> restResourceTags(Class... classes){
		return PStackX.fromCollection(concat(Stream.of(classes).collect(Collectors.toList()),defaultTags));
	}

	private static Class toClass(String cl) {
		try {
			return Class.forName(cl);
		} catch (ClassNotFoundException e) {
			logger.error("Class not found for {}", cl);
			ExceptionSoftener.throwSoftenedException(e);
		}
		return null;
	}
}
