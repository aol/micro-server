package com.aol.micro.server.spring.datasource.hibernate;

import static org.springframework.util.ReflectionUtils.findField
import static org.springframework.util.ReflectionUtils.getField
import static org.springframework.util.ReflectionUtils.makeAccessible
import groovy.transform.CompileStatic

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@CompileStatic
@Component
public class DAOProvider<T,ID extends Serializable> {

	private final ApplicationContext applicationContext;
	
	@Autowired
	public DAOProvider(ApplicationContext applicationContext){
		this.applicationContext = applicationContext
	}
	
	public GenericHibernateService<T,ID> get(Class<T> targetType){
		return setTargetType(applicationContext.getBean(GenericHibernateService.class),targetType);
	}
	private  GenericHibernateService<T, ID> setTargetType(GenericHibernateService bean,Class<T> targetType) {
		bean.genericDao.@persistentClass =targetType;
		return bean;
	}
}


