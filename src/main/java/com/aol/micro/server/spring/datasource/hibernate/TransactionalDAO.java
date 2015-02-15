package com.aol.micro.server.spring.datasource.hibernate;

import java.io.Serializable;

import javax.transaction.Transactional;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Component
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TransactionalDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {
	
	@Delegate
	@Getter(AccessLevel.PACKAGE)
	private final GenericDAOImpl<T, ID> genericDao;
	
	
	@Autowired
	public TransactionalDAO(ApplicationContext context){
		
			this.genericDao =extractValue(context);
		
		
	}
	
	private GenericDAOImpl<T,ID> extractValue(ApplicationContext context){
		return context.getBean(GenericDAOImpl.class);
	}

}
