package com.aol.micro.server.spring;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.ModuleBean;
import com.aol.micro.server.rest.RestResources;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.spring.annotations.Microserver;
import com.aol.micro.server.utility.UsefulStaticMethods;

class SpringApplicationConfigurator {
	
	public static AnnotationConfigWebApplicationContext createSpringApp(Class...classes)  {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.setAllowCircularReferences(false);
		rootContext.register(classes);
		
		Optional<Package> basePackage = Stream.of(classes)
				.filter(cl -> cl.getAnnotation(Microserver.class)!=null)
				.map(cl -> cl.getPackage()).findAny();
		basePackage.ifPresent( base->
		rootContext.scan(Stream.of(classes)
				.map(cl -> cl.getAnnotation(Microserver.class))
				.filter(ano -> ano!=null)
				.map(ano -> ((Microserver)ano).basePackages())
				.map(packages -> UsefulStaticMethods.eitherArray(packages,new String[]{base.getName()}))
				.peek(packages -> Stream.of(packages).forEach(it->System.out.println(it)) )
				.findFirst().get()));
			

		
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
