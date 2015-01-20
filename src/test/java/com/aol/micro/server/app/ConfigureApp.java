package com.aol.micro.server.app;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.ModuleBean;
import com.aol.micro.server.ModuleConfiguration;

@Configuration
public class ConfigureApp  implements ModuleConfiguration{

	@Value("${app.port:8080}")
	@Getter
	int port;

	@Override
	@Bean(name = "appModule")
	public ModuleBean configure() {
		return ModuleBean.builder().port(port).module(() -> "test-app").build();
	}
}
