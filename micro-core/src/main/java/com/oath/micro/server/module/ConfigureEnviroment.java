package com.aol.micro.server.module;

import java.util.Collection;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureEnviroment {

	@Autowired(required = false)
	private Collection<ModuleBean> modules;

	@Bean
	public Environment environment(@Qualifier("propertyFactory") Properties props) {
		if (modules == null) {
			return new Environment(props);
		}
		return new Environment(props, modules);
	}

}
