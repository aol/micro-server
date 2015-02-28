package com.aol.micro.server.spring.datasource.hibernate;


import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.googlecode.genericdao.dao.hibernate.GenericDAOAccessor;

@Component
public class DAOProvider<T,ID extends Serializable> {

	private final ApplicationContext applicationContext;
	
	@Autowired
	public DAOProvider(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
	}
	
	public GenericHibernateService<T,ID> get(Class<T> targetType){
		return setTargetType(applicationContext.getBean(GenericHibernateService.class),targetType);
	}
	private  GenericHibernateService<T, ID> setTargetType(GenericHibernateService bean,Class<T> targetType) {
		GenericDAOAccessor.setTargetType(bean.getGenericDao(), targetType);
		return bean;
		
	}
}


