package com.aol.micro.server.spring;

import org.springframework.context.ConfigurableApplicationContext;

import com.aol.micro.server.config.Config;

public interface SpringBuilder {
	public ConfigurableApplicationContext createSpringApp(Config config, Class...classes);
}
