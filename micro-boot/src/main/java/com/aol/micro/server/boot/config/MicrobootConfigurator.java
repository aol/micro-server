package com.aol.micro.server.boot.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.Configurer;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.nurkiewicz.lazyseq.LazySeq;

public class MicrobootConfigurator implements Configurer{
	

		public Config buildConfig(Class class1) {
			Microboot microserver = (Microboot)class1.getAnnotation(Microboot.class);
			if(microserver==null)
				return Config.instance();
			List<Class> classes = buildClasses(class1, microserver);
			
			Map<String, String> properties = buildProperties(microserver);
			
			return Config.instance().withEntityScan(microserver.entityScan()).withClasses(ImmutableSet.copyOf(classes))
					.withPropertiesName(microserver.propertiesName()).withProperties(ImmutableMap.copyOf(properties)).set();
		}

		private Map<String, String> buildProperties(Microboot microserver) {
			Map<String,String> properties = LazySeq.of(microserver.properties())
											.grouped(2)
											.stream()
											.collect(Collectors.toMap(prop -> prop.get(0), prop -> prop.get(1)));
			return properties;
		}

		private List<Class> buildClasses(Class class1, Microboot microserver) {
			List<Class> classes = new ArrayList();
			classes.add(class1);
			if(microserver.classes()!=null)
				classes.addAll(Arrays.asList(microserver.classes()));
			Stream.of(microserver.springClasses()).map(cl -> cl.getClasses()).forEach(array -> classes.addAll(Arrays.asList(array)));
			return classes;
		}
	}

