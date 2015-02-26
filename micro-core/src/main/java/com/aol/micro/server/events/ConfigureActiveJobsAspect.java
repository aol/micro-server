package com.aol.micro.server.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureActiveJobsAspect {
	@Autowired
	private EventBus bus; 
	private @Value("${system.logging.max.per.hour:10}") int maxLoggingCapacity;
	private @Value("${system.request.capture:true}") boolean requestCapture;
	
	
	@Bean
	public JobsBeingExecuted jobsBeingExecuted(){
		return new JobsBeingExecuted(bus, maxLoggingCapacity);
	}
	
	@Bean
	public RequestsBeingExecuted requestsBeingExecuted(){
		return new RequestsBeingExecuted(bus,requestCapture);
	}
	
	
}
