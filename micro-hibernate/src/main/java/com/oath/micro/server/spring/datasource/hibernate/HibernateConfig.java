package com.oath.micro.server.spring.datasource.hibernate;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.oath.micro.server.config.ConfigAccessor;
import com.oath.micro.server.spring.datasource.JdbcConfig;

import java.util.Arrays;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Resource(name="mainEnv")
	private JdbcConfig env;
	@Resource(name="mainDataSource")
	private DataSource dataSource;
	
	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
		return transactionManager;
	}
	
	@Bean
	public SessionFactory sessionFactory(){
		return HibernateSessionBuilder.builder()
				.packages(new ConfigAccessor().get().getDataSources().getOrElse(new ConfigAccessor().get()
									.getDefaultDataSourceName(), Arrays.asList()))
				.env(env)
				.dataSource(dataSource)
				.build().sessionFactory();
	}
	
	
	
	

}
