package com.aol.micro.server.rest.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.aol.micro.server.events.JobsBeingExecuted;
import com.aol.micro.server.events.RequestsBeingExecuted;

public class ConfigureResources {
	
	@Autowired
	private List<RequestsBeingExecuted> activeQueries;
	@Autowired
	private JobsBeingExecuted activeJobs;
	
	@Bean
	public ActiveResource activeResource(){
		return new ActiveResource(activeQueries,activeJobs);
	}
	
	@Bean
	public ManifestResource manifest(){
		return new ManifestResource();
	}
}
