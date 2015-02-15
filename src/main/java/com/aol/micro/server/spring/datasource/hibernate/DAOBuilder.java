package com.aol.micro.server.spring.datasource.hibernate;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.experimental.Builder;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Builder
@AllArgsConstructor
public class DAOBuilder {

	
	private final SessionFactory factory;
	private final ApplicationContext applicationContext;

	
	public DAOProvider daoProvider() {
		return new DAOProvider(applicationContext);
	}
	
	
	public<T,ID extends Serializable> GenericDAOImpl<T,ID> dao(){
		GenericDAOImpl<T,ID> dao = new GenericDAOImpl<T,ID>();
		dao.setSessionFactory(factory);
		return dao;
	}
}
