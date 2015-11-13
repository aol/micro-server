package com.aol.micro.server.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.pcollections.HashTreePMap;
import org.pcollections.HashTreePSet;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.PluginLoader;
import com.nurkiewicz.lazyseq.LazySeq;

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
		Map<String, String> properties = LazySeq.of(microserver.properties()).grouped(2).stream()
				.collect(Collectors.toMap(prop -> prop.get(0), prop -> prop.get(1)));
		return properties;
	}

	private List<Class> buildClasses(Class class1, Microserver microserver) {
		List<Class> classes = new ArrayList();
		Set<Class> blackList = Arrays.stream(microserver.blacklistedClasses()).collect(Collectors.toSet());

		classes.add(class1);
		if (microserver.classes() != null)
			classes.addAll(Arrays.asList(microserver.classes()));
		List<Plugin> modules = PluginLoader.INSTANCE.plugins.get();
		if(modules.size()>0)
			classes.addAll(SequenceM.fromStream(modules.stream()).flatMap(module -> module.springClasses().stream()).toList());
		
		return classes.stream().filter(clazz -> !blackList.contains(clazz)).collect(Collectors.toList());
	}

	public Set<Class> vetClasses(Class class1,Set<Class> coreClasses) {
		Microserver microserver = (Microserver) class1.getAnnotation(Microserver.class);
		if(microserver==null)
			return coreClasses;
		Set<Class> blackList = Arrays.stream(microserver.blacklistedClasses()).collect(Collectors.toSet());
		return coreClasses.stream().filter(clazz -> !blackList.contains(clazz)).collect(Collectors.toSet());
	}
}
