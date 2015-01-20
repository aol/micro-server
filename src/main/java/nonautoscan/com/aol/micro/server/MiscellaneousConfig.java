package nonautoscan.com.aol.micro.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
public class MiscellaneousConfig {

	@Bean
	public EventBus eventBus() {
		return new EventBus();
	}

}
