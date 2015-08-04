package com.aol.micro.server.boot.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;

import com.aol.cyclops.lambda.monads.SequenceM;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.Configurer;

import com.nurkiewicz.lazyseq.LazySeq;

public class MicrobootConfigurator implements Configurer{
	

		public Config buildConfig(Class class1) {
			Microboot microserver = (Microboot)class1.getAnnotation(Microboot.class);
			if(microserver==null)
				return Config.instance();
			List<Class> classes = buildClasses(class1, microserver);
			
			Map<String, String> properties = buildProperties(microserver);
			
			return Config.instance().withEntityScan(microserver.entityScan()).withClasses(HashTreePSet.from(classes))
					.withPropertiesName(microserver.propertiesName()).withProperties(HashTreePMap.from(properties)).set();
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
			List<Plugin> modules = PluginLoader.INSTANCE.plugins.get();
			if(modules.size()>0)
				classes.addAll(SequenceM.fromStream(modules.stream()).flatMap(module -> module.springClasses().stream()).toList());
			return classes;
		}
	}

