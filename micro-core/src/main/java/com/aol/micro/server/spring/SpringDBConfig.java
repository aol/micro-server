package com.aol.micro.server.spring;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;

public interface SpringDBConfig {
	
	void createSpringApp(String name);
	void setBeanFactory(
			ConfigurableListableBeanFactory beanFactory);

	

	

	void setConfig(Config config);

	void setRootContext(
			AnnotationConfigWebApplicationContext rootContext);

}