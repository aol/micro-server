package com.aol.micro.server.spring.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerException;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.module.Environment;
import com.aol.micro.server.module.ModuleBean;
import com.aol.micro.server.servers.AccessLogLocationBean;
import com.aol.micro.server.spring.datasource.hibernate.DAOBuilder;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.properties.PropertyFileConfig;


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



	public DAOProvider buildDAOProvider(
			AnnotationConfigWebApplicationContext rootContext,
			SessionFactory sessionFactory) {

		return DAOBuilder.builder().applicationContext(rootContext)
				.factory(sessionFactory).build().daoProvider();
	}

	
	@Bean
	public EmbeddedServletContainerFactory servletContainer() {

		return (initializers) -> {
			return new Container();
		};
		

	}

	static class Container implements EmbeddedServletContainer {

		@Override
		public void start() throws EmbeddedServletContainerException {
		

		}

		@Override
		public void stop() throws EmbeddedServletContainerException {
		
		}

		@Override
		public int getPort() {
		
			return 0;
		}

	}

}
