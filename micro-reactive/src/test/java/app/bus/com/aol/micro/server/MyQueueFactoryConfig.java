package app.bus.com.aol.micro.server;

import com.oath.cyclops.async.QueueFactories;
import com.oath.cyclops.async.adapters.QueueFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MyQueueFactoryConfig {

	
	@Bean
	public QueueFactory responderQueueFactory(){
		return QueueFactories.boundedNonBlockingQueue(1000);
	}
}
