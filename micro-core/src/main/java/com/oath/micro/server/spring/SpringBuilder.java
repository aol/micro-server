package com.oath.micro.server.spring;

import org.springframework.context.ConfigurableApplicationContext;

import com.oath.micro.server.config.Config;

public interface SpringBuilder {
	public ConfigurableApplicationContext createSpringApp(Config config, Class...classes);

	public Class[] classes(Config config, Class...classes);
}
