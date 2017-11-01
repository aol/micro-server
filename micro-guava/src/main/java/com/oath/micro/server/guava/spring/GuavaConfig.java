package com.oath.micro.server.guava.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
public class GuavaConfig {

	@Bean
	public EventBus microserverEventBus() {
		return new EventBus();
	}

	
	
	
}
