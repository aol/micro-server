package com.aol.micro.server.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.nurkiewicz.lazyseq.LazySeq;

public class MicroserverConfigurer {

	public Config buildConfig(Class class1) {
		Microserver microserver = (Microserver)class1.getAnnotation(Microserver.class);
		List<Class> classes = buildClasses(class1, microserver);
		
		Map<String, String> properties = buildProperties(microserver);
		
		return Config.get().withEntityScan(microserver.entityScan()).withClasses(ImmutableList.copyOf(classes))
				.withPropertiesName(microserver.propertiesName()).withProperties(ImmutableMap.copyOf(properties)).set();
	}

	private Map<String, String> buildProperties(Microserver microserver) {
		Map<String,String> properties = LazySeq.of(microserver.properties())
										.grouped(2)
										.stream()
										.collect(Collectors.toMap(prop -> prop.get(0), prop -> prop.get(1)));
		return properties;
	}

	private List<Class> buildClasses(Class class1, Microserver microserver) {
		List<Class> classes = new ArrayList();
		classes.add(class1);
		if(microserver.classes()!=null)
			classes.addAll(Arrays.asList(microserver.classes()));
		Stream.of(microserver.springClasses()).map(cl -> cl.getClasses()).forEach(array -> classes.addAll(Arrays.asList(array)));
		return classes;
	}
}
