package com.aol.micro.server.reactive;

import java.util.concurrent.Executors;

import cyclops.async.QueueFactories;
import cyclops.async.adapters.QueueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ResponderConfigurer<T> {

	
	@Value("${responder.threads:0}")
	int threads;
	
	
	@Autowired(required=false)
	@Qualifier(value="responderQueueFactory")
	private QueueFactory<T> factory;
	
	
	@Bean
	public EventQueueManager<T> responder(){
		if(threads==0)
			threads= Runtime.getRuntime().availableProcessors();
		if(threads==-1)
			threads=0;
		if(factory==null)
			factory = QueueFactories.unboundedQueue();
		
		return EventQueueManager.of(Executors.newFixedThreadPool(threads),factory);
		
	}
}
