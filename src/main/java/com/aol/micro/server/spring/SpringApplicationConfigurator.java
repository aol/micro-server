package com.aol.micro.server.spring;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.Module;
import com.aol.micro.server.module.ModuleBean;
import com.aol.micro.server.rest.RestResources;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

class SpringApplicationConfigurator {
	
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



	
}
