package nonautoscan.com.aol.micro.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;

import com.aol.micro.server.rest.client.nio.SpringRestTemplate;
import com.google.common.eventbus.EventBus;

@Configuration
public class MiscellaneousConfig {

	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}

	
	@Bean
	public SpringRestTemplate restClient(){
		return new SpringRestTemplate(new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory()));
	}
	
}
