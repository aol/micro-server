package com.aol.micro.server.servers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.Environment;
import com.aol.micro.server.Module;
import com.aol.micro.server.ModuleBean;
import com.aol.micro.server.rest.RestResource;
import com.aol.micro.server.rest.RestResources;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class SpringApplicationCreator {
	
	public static AnnotationConfigWebApplicationContext createSpringApp(Class...classes)  {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setAllowCircularReferences(false);
			
		rootContext.register(classes);
		rootContext.refresh();
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) rootContext).getBeanFactory();
		beanFactory.registerSingleton(Environment.class.getCanonicalName(), createEnvironment( rootContext));
		beanFactory.registerSingleton(AccessLogLocationBean.class.getCanonicalName(), createAccessLogLocationBean( rootContext));
		beanFactory.registerSingleton(RestResources.class.getCanonicalName(), createRestResources( rootContext));
	
		return rootContext;
	}
	
	

	private static RestResources createRestResources(
			AnnotationConfigWebApplicationContext rootContext) {
		return new RestResources(rootContext.getBeansOfType(RestResource.class).values());
	}



	private static AccessLogLocationBean createAccessLogLocationBean(
			AnnotationConfigWebApplicationContext rootContext) {
		Properties props = (Properties)rootContext.getBean("propertyFactory");
		String location = Optional.ofNullable((String)props.get("access.log.output")).orElse("./logs/");
		return new AccessLogLocationBean(location);
	}



	private static Environment createEnvironment(
			AnnotationConfigWebApplicationContext rootContext) {
		Properties props = (Properties)rootContext.getBean("propertyFactory");
		Map<String,ModuleBean> moduleDefinitions =  rootContext.getBeansOfType(ModuleBean.class);
		if(moduleDefinitions ==null)
			return new Environment(props);
		return new Environment(props,moduleDefinitions.values());
		
	}



	public static ImmutableList<RestResource> createJerseyApp(AnnotationConfigWebApplicationContext rootContext,Module type){
		
		return ImmutableList.copyOf(rootContext.getBeansOfType(type.getRestResourceClass()).values());
	}
}
