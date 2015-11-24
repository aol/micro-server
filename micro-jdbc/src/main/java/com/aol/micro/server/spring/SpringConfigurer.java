package com.aol.micro.server.spring;

import java.util.Properties;

import javax.sql.DataSource;

import lombok.Setter;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.datasource.DataDataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;

public class SpringConfigurer implements SpringDBConfig {

	@Setter
	private ConfigurableListableBeanFactory beanFactory;
	@Setter
	private JdbcConfig jdbc;
	@Setter
	private DataSource dataSource;
	@Setter
	private Config config;
	@Setter
	private AnnotationConfigWebApplicationContext rootContext;

	
	public void createSpringApp(String name) {

		JdbcConfig jdbc = buildJdbcProperties(rootContext, name);
		if(rootContext.containsBean(name+"dataSource"))
			dataSource = (DataSource)rootContext.getBean(name+"dataSource");
		else
			dataSource = buildDataSource(name, jdbc);
		beanFactory.registerSingleton(name + "DataSource", dataSource);
		
		
		
	}

	private DataSource buildDataSource(String name, JdbcConfig jdbc) {
		return DataDataSourceBuilder.builder().env(jdbc).build().mainDataSource();
	}

	private JdbcConfig buildJdbcProperties(AnnotationConfigWebApplicationContext rootContext, String name) {
		return JdbcConfig.builder().properties((Properties) rootContext.getBean("propertyFactory")).name(name).build();
	}

	
}
