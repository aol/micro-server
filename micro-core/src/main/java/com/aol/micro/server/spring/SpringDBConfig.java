package com.aol.micro.server.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.datasource.JdbcConfig;

public interface SpringDBConfig {
	
	void createSpringApp(String name);
	void setBeanFactory(
			ConfigurableListableBeanFactory beanFactory);

	void setJdbc(JdbcConfig jdbc);

	 void setDataSource(DataSource dataSource);

	void setConfig(Config config);

	void setRootContext(
			AnnotationConfigWebApplicationContext rootContext);

}