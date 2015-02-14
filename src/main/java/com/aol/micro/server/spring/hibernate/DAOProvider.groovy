package com.aol.micro.server.spring.hibernate;

import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import groovy.transform.CompileStatic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.sun.xml.internal.bind.v2.model.core.ID;

@CompileStatic
public class DAOProvider<T,ID extends Serializable> {

	private final ApplicationContext applicationContext;
	
	public DAOProvider(ApplicationContext applicationContext){
		this.applicationContext = applicationContext
	}
	
	public TransactionalDAO<T,ID> get(Class<T> targetType){
		return setTargetType(applicationContext.getBean(TransactionalDAO.class),targetType);
	}
	private  TransactionalDAO<T, ID> setTargetType(TransactionalDAO bean,Class<T> targetType) {
		bean.genericDao.@persistentClass =targetType;
		return bean;
	}
}


