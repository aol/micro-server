package com.aol.micro.server.spring;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.ErrorCode;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class SpringContextFactory {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ImmutableSet<Class> classes;
	
	public SpringContextFactory(Class c, Set<Class> classes){
		Set s = Sets.newHashSet(classes);
		s.add(c);
		this.classes = ImmutableSet.copyOf(s);
	}
	
	public SpringContextFactory(List<Class> additionalClasses,Set<Class> classes) {
		Set s = Sets.newHashSet(classes);
		s.addAll(additionalClasses);
		this.classes = ImmutableSet.copyOf(s);
	}

	public AnnotationConfigWebApplicationContext createSpringContext() {
		try {
			AnnotationConfigWebApplicationContext springContext = new SpringApplicationConfigurator().createSpringApp(classes.toArray(new Class[0]));
			return springContext;
		} catch (Exception e) {
			logger.error( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION.toString(),e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
	
}
