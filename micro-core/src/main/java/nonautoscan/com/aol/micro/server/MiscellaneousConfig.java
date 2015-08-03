package nonautoscan.com.aol.micro.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

import com.google.common.eventbus.EventBus;

@Configuration
public class MiscellaneousConfig {

	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}

	
	
	
}
