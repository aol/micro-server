package com.aol.micro.server.spring.datasource.hibernate;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aol.micro.server.config.ConfigAccessor;
import com.aol.micro.server.spring.datasource.JdbcConfig;

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
				.packages(new ConfigAccessor().get().getDataSources().get(new ConfigAccessor().get()
									.getDefaultDataSourceName()))
				.env(env)
				.dataSource(dataSource)
				.build().sessionFactory();
	}
	
	
	
	

}
