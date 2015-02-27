package com.aol.micro.server.spring;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.aol.micro.server.ErrorCode;
import com.aol.micro.server.config.Config;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

@AllArgsConstructor
public class SpringContextFactory {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ImmutableSet<Class> classes;
	private final Config config;
	@Wither
	private final SpringBuilder springBuilder;
	
	public SpringContextFactory(Config config, Class c, Set<Class> classes){
		Set s = Sets.newHashSet(classes);
		s.add(c);
		s.addAll(config.getClasses());
		this.classes = ImmutableSet.copyOf(s);
		this.config = config;
		springBuilder =  new SpringApplicationConfigurator();
	}
	
	public SpringContextFactory(Config config, Set<Class> classes) {
		Set s = Sets.newHashSet(classes);
		s.addAll(config.getClasses());
		this.classes = ImmutableSet.copyOf(s);
		this.config=config;
		springBuilder =  new SpringApplicationConfigurator();
	}

	public ApplicationContext createSpringContext() {
		try {
			ApplicationContext springContext = springBuilder.createSpringApp(config,classes.toArray(new Class[0]));
			return springContext;
		} catch (Exception e) {
			logger.error( ErrorCode.STARTUP_FAILED_SPRING_INITIALISATION.toString(),e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
	
}
