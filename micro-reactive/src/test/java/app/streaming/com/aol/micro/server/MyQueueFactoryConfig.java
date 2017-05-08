package app.streaming.com.aol.micro.server;

import cyclops.async.QueueFactories;
import cyclops.async.QueueFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyQueueFactoryConfig {

	
	@Bean
	public QueueFactory responderQueueFactory(){
		return QueueFactories.boundedNonBlockingQueue(1000);
	}
}
