package com.aol.micro.server.spring;

import javax.sql.DataSource;

import lombok.Setter;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.HibernateSessionBuilder;

public class SpringConfigurer implements SpringDBConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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

		
			SessionFactory sessionFactory = buildSession(name, config, dataSource, jdbc);
			beanFactory.registerSingleton(name + "DataSource", dataSource);
			beanFactory.registerSingleton(name + "SessionFactory", sessionFactory);
			beanFactory.registerSingleton(name + "HibernateTransactionManager", buildTransactionManager(name, config, dataSource, jdbc));
		
	}

	

	private HibernateTransactionManager buildTransactionManager(String name, Config config, DataSource dataSource, JdbcConfig jdbc) {
		return HibernateSessionBuilder.builder().packages(config.getDataSources().get(name)).dataSource(dataSource).env(jdbc).build()
				.transactionManager();
	}

	private SessionFactory buildSession(String name, Config config, DataSource dataSource, JdbcConfig jdbc) {

		return HibernateSessionBuilder.builder().packages(config.getDataSources().get(name)).dataSource(dataSource).env(jdbc).build()
				.sessionFactory();
	}
}
