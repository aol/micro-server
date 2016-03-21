package com.aol.micro.server.reactive;

import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.async.QueueFactories;
import com.aol.cyclops.data.async.QueueFactory;

@Configuration
public class ResponderConfigurer {

	
	@Value("${responder.threads:0}")
	int threads;
	
	
	@Autowired(required=false)
	@Qualifier(value="responderQueueFactory")
	QueueFactory factory;
	
	
	@Bean
	public EventQueueManager responder(){
		if(threads==0)
			threads= Runtime.getRuntime().availableProcessors();
		if(threads==-1)
			threads=0;
		if(factory==null)
			factory = QueueFactories.unboundedQueue();
		
		return EventQueueManager.of(Executors.newFixedThreadPool(threads),factory);
		
	}
}
