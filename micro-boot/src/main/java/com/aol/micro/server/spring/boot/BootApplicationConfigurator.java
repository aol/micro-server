package com.aol.micro.server.spring.boot;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.SpringBuilder;

public class BootApplicationConfigurator implements SpringBuilder {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public ConfigurableApplicationContext createSpringApp(Config config, Class...classes)  {
		
		
		List<Class> classList = new ArrayList<Class>();
		classList.addAll(Arrays.asList(classes));
		classList.add(JerseyApplication.class);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(classList.toArray(new Class[0]));
		new JerseyApplication(classList).config(builder);
		
		return builder.application().run();
	}
	

}
