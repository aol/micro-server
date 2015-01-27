package com.aol.micro.server.spring;

import java.util.List;

import nonautoscan.com.aol.micro.server.AopConfig;
import nonautoscan.com.aol.micro.server.MiscellaneousConfig;
import nonautoscan.com.aol.micro.server.PropertyFileConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.ErrorCode;
import com.google.common.collect.Lists;

public class SpringContextFactory {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final List<Class> classes = Lists.newArrayList(PropertyFileConfig.class,
			MiscellaneousConfig.class, AopConfig.class);
	
	public SpringContextFactory(Class c){
		classes.add(c);
	}
	
	public SpringContextFactory(List<Class> additionalClasses) {
		classes.addAll(additionalClasses);
	}

	public AnnotationConfigWebApplicationContext createSpringContext() {
		try {
			AnnotationConfigWebApplicationContext springContext = SpringApplicationConfigurator.createSpringApp(classes.toArray(new Class[0]));
			return springContext;
		} catch (Exception e) {
			logger.error( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION.toString(),e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
	
}
