package com.aol.micro.server.spring.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import app.hibernate.com.aol.micro.server.HibernateEntity;
import app.rest.client.com.aol.micro.server.MyEntity;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Configuration
public class ConfigureDAO {

	@Autowired
	private SessionFactory factory;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public DAOProvider daoProvider() {
		return new DAOProvider(applicationContext);
	}
	
	@Bean
	public GenericDAOImpl<HibernateEntity,Long> dao(){
		GenericDAOImpl<HibernateEntity,Long> dao = new GenericDAOImpl<HibernateEntity,Long>();
		dao.setSessionFactory(factory);
		return dao;
	}
}
