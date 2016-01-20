package com.aol.micro.server.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.aol.micro.server.module.Environment;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.spring.properties.PropertyFileConfig;


@Configuration
@PropertySource("classpath:spring-boot-microserver.properties")
public class JerseySpringBootFrontEndApplication extends SpringBootServletInitializer {

	List<Class> classes;

	public JerseySpringBootFrontEndApplication(){
		classes = new ArrayList<>();
	}
	
	public JerseySpringBootFrontEndApplication(List<Class> classes2) {
		classes = new ArrayList<>();
		classes.addAll(classes2);
		classes.add(JerseySpringBootFrontEndApplication.class);
		classes.add(PropertyFileConfig.class);
		classes.add(Environment.class);
		classes.add(AccessLogLocationBean.class);
	}

	
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
	
		return application.sources(classes.toArray(new Class[0]));
	}

	public SpringApplicationBuilder config(SpringApplicationBuilder builder) {
		return configure(builder);

	}

	@Bean
	public AccessLogLocationBean createAccessLogLocationBean(
			ApplicationContext rootContext) {
		Properties props = (Properties) rootContext.getBean("propertyFactory");
		String location = Optional.ofNullable(
				(String) props.get("access.log.output")).orElse("./logs/");
		return new AccessLogLocationBean(location);
	}



	

	
	
}
