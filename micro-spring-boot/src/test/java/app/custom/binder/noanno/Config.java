package app.custom.binder.noanno;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oath.micro.server.auto.discovery.JaxRsResourceWrapper;

@Configuration
public class Config {

	@Bean
	public JaxRsResourceWrapper<CustomBinder2> binder(){
		return JaxRsResourceWrapper.jaxRsResource(new CustomBinder2());
	}
}
