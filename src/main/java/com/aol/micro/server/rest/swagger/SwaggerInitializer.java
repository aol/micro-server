package com.aol.micro.server.rest.swagger;

import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Application;

import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

import scala.collection.JavaConversions;
import scala.collection.immutable.List;

import com.aol.micro.server.auto.discovery.RestResource;
import com.aol.micro.server.servers.model.ServerData;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

public class SwaggerInitializer implements ServletContextListener {

	private final List<Class<?>> resourceClasses;
	private final String baseUrlPattern;
	

	
	public SwaggerInitializer(ServerData serverData) {
		this.resourceClasses = JavaConversions.asScalaBuffer(
				serverData.getResources()
				.stream()
				.map(resource -> resource.getClass())
				.collect(Collectors.<Class<?>> toList())).toList();
		this.baseUrlPattern = serverData.getBaseUrlPattern();
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		BeanConfig beanConfig = new BeanConfig() {
			@Override
			public List<Class<?>> classesFromContext(Application app, ServletConfig sc) {
				return resourceClasses;
			}
		};
		beanConfig.setVersion("1.0.2");
		beanConfig.setBasePath(baseUrlPattern);
		beanConfig.setDescription("RESTful resources");
		beanConfig.setTitle("RESTful API");
		beanConfig.setScan(true);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
