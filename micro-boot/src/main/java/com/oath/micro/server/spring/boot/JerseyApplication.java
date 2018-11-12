package com.oath.micro.server.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.oath.micro.server.module.MicroserverEnvironment;
import com.oath.micro.server.servers.AccessLogLocationBean;
import com.oath.micro.server.spring.properties.PropertyFileConfig;


@Configuration
@PropertySource("classpath:spring-boot-microserver.properties")
public class JerseyApplication extends SpringBootServletInitializer {

	List<Class> classes;

	public JerseyApplication(){
		classes = new ArrayList<>();
	}
	
	public JerseyApplication(List<Class> classes2) {
		classes = new ArrayList<>();
		classes.addAll(classes2);
		classes.add(JerseyApplication.class);
		classes.add(PropertyFileConfig.class);
		classes.add(MicroserverEnvironment.class);
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



	

	
	@Bean
	public ServletWebServerFactory servletContainer() {

		return (initializers) -> {
			return new Container();
		};
		

	}

	static class Container implements WebServer {

		@Override
		public void start() throws WebServerException {
		

		}

		@Override
		public void stop() throws WebServerException {
		
		}

		@Override
		public int getPort() {
		
			return 0;
		}

	}

}
