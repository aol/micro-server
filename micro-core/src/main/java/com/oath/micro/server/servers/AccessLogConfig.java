package com.aol.micro.server.servers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccessLogConfig {

	@Value("${access.log.output:./logs}")
	private String accessLogLocation;

	@Bean
	public AccessLogLocationBean accessLogLocationBean() {
		return new AccessLogLocationBean(accessLogLocation);
	}
}
