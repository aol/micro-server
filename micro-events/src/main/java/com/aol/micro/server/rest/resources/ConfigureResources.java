package com.aol.micro.server.rest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.events.RequestsBeingExecuted;

@Configuration
public class ConfigureResources {

	@Autowired
	private List<RequestsBeingExecuted> activeQueries;

	@Bean
	public ActiveResource activeResource(@Qualifier("jobsBeingExecuted") JobsBeingExecuted jobsBeingExecuted) {
		return new ActiveResource(activeQueries, jobsBeingExecuted);
	}

	@Bean
	public ManifestResource manifest() {
		return new ManifestResource();
	}
}
