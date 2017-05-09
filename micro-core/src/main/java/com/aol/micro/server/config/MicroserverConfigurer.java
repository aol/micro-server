package com.aol.micro.server.config;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import cyclops.stream.ReactiveSeq;
import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MicroserverConfigurer implements Configurer {

	public Config buildConfig(Class class1) {
		Microserver microserver = (Microserver) class1.getAnnotation(Microserver.class);
		
		 
		if (microserver == null){
			microserver = Microserver.Instance.class.getAnnotation(Microserver.class);
			
		}
		String[] basePackages=microserver.basePackages();
		if(basePackages.length==0){
			String[] basePackagesFromClass ={class1.getPackage().getName()};
			basePackages = basePackagesFromClass;
		}
		
		List<Class> classes = buildClasses(class1, microserver);

		Map<String, String> properties = buildProperties(microserver);

		return Config.instance().withBasePackages(basePackages).withEntityScan(microserver.entityScan()).withClasses(HashTreePSet.from(classes))
				.withPropertiesName(microserver.propertiesName()).withInstancePropertiesName(microserver.instancePropertiesName())
				.withServiceTypePropertiesName(microserver.serviceTypePropertiesName())
				.withAllowCircularReferences(microserver.allowCircularDependencies()).withProperties(HashTreePMap.from(properties)).set();
	}

	private Map<String, String> buildProperties(Microserver microserver) {
		Map<String, String> properties = ReactiveSeq.of(microserver.properties())
													.grouped(2)
													.toMap(prop -> prop.get(0), prop -> prop.get(1));
		return properties;
	}

	private List<Class> buildClasses(Class class1, Microserver microserver) {
		List<Class> classes = new ArrayList();

		classes.add(class1);
		if (microserver.classes() != null)
			classes.addAll(Arrays.asList(microserver.classes()));
		List<Plugin> modules = PluginLoader.INSTANCE.plugins.get();
		if(modules.size()>0)
			classes.addAll(ReactiveSeq.fromStream(modules.stream()).flatMap(module -> module.springClasses().stream()).toList());
		
		return classes;
	}

}
