package com.aol.micro.server.spring.datasource.hibernate;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.config.ConfigAccessor;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

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
	@Bean
	public<T,ID extends Serializable> GenericDAOImpl<T,ID> genericDAOImpl(){
		GenericDAOImpl<T,ID> dao = new GenericDAOImpl<T,ID>();
		dao.setSessionFactory(sessionFactory());
		return dao;
	}
	
	
	

}
