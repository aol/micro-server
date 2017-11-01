package com.oath.micro.server.spring;

import java.util.Arrays;
import java.util.Properties;

import javax.sql.DataSource;

import lombok.Setter;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.oath.micro.server.config.Config;
import com.oath.micro.server.spring.datasource.JdbcConfig;
import com.oath.micro.server.spring.datasource.hibernate.HibernateSessionBuilder;

public class HibernateSpringConfigurer implements SpringDBConfig {

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
		DataSource dataSource = null;
		if(rootContext.containsBean(name+"dataSource"))
			dataSource = (DataSource)rootContext.getBean(name+"dataSource");
		
		SessionFactory sessionFactory = buildSession(name, config, dataSource, jdbc);
		
		beanFactory.registerSingleton(name + "SessionFactory", sessionFactory);
		beanFactory.registerSingleton(name + "HibernateTransactionManager", buildTransactionManager(name, config, dataSource, jdbc));

	}	

	private JdbcConfig buildJdbcProperties(AnnotationConfigWebApplicationContext rootContext, String name) {
		return JdbcConfig.builder().properties((Properties) rootContext.getBean("propertyFactory")).name(name).build();
	}

	private HibernateTransactionManager buildTransactionManager(String name, Config config, DataSource dataSource, JdbcConfig jdbc) {
		return HibernateSessionBuilder.builder().packages(config.getDataSources().getOrElse(name, Arrays.asList())).dataSource(dataSource).env(jdbc).build()
				.transactionManager();
	}

	private SessionFactory buildSession(String name, Config config, DataSource dataSource, JdbcConfig jdbc) {

		return HibernateSessionBuilder.builder().packages(config.getDataSources().getOrElse(name,Arrays.asList())).dataSource(dataSource).env(jdbc).build()
				.sessionFactory();
	}
}
