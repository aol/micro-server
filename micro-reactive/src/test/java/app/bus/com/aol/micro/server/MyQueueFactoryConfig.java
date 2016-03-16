package app.bus.com.aol.micro.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.data.async.QueueFactories;
import com.aol.cyclops.data.async.QueueFactory;

@Configuration
public class MyQueueFactoryConfig {

	
	@Bean
	public QueueFactory responderQueueFactory(){
		return QueueFactories.boundedNonBlockingQueue(1000);
	}
}
