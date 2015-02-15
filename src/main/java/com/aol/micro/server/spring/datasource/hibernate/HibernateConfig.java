package com.aol.micro.server.spring.datasource.hibernate;

import java.io.Serializable;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aol.micro.server.Config;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Configuration
@EnableTransactionManagement
@ImportResource("classpath*:roma-context.xml")
public class HibernateConfig {

	@Autowired
	private JdbcConfig env;
	@Autowired
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
				.packages(Config.get().getDataSourcePackages().get("db"))
				.env(env)
				.dataSource(dataSource)
				.build().sessionFactory();
	}
	@Bean
	public<T,ID extends Serializable> GenericDAOImpl<T,ID> dao(){
		GenericDAOImpl<T,ID> dao = new GenericDAOImpl<T,ID>();
		dao.setSessionFactory(sessionFactory());
		return dao;
	}

	

}
