package com.aol.micro.server.spring.datasource.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import lombok.AccessLevel;
import lombok.Getter;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

@Component
@Transactional
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GenericHibernateService<T, ID extends Serializable> implements GenericDAO<T, ID> {

	@Getter(AccessLevel.PACKAGE)
	private final GenericDAOImpl<T, ID> genericDao;

	@Autowired
	public GenericHibernateService(ApplicationContext context) {

		this.genericDao = extractValue(context);

	}

	private GenericDAOImpl<T, ID> extractValue(ApplicationContext context) {
		return (GenericDAOImpl<T, ID>) context.getBean("genericDAOImpl");
	}

	public int count(ISearch search) {
		return genericDao.count(search);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		genericDao.setSessionFactory(sessionFactory);
	}

	public T find(Serializable id) {
		return genericDao.find(id);
	}

	public T[] find(Serializable... ids) {
		return genericDao.find(ids);
	}

	public List<T> findAll() {
		return genericDao.findAll();
	}

	public void flush() {
		genericDao.flush();
	}

	public T getReference(Serializable id) {
		return genericDao.getReference(id);
	}

	public T[] getReferences(Serializable... ids) {
		return genericDao.getReferences(ids);
	}

	public boolean isAttached(T entity) {
		return genericDao.isAttached(entity);
	}

	public void refresh(T... entities) {
		genericDao.refresh(entities);
	}

	public boolean remove(T entity) {
		return genericDao.remove(entity);
	}

	public void remove(T... entities) {
		genericDao.remove(entities);
	}

	public boolean removeById(Serializable id) {
		return genericDao.removeById(id);
	}

	public void removeByIds(Serializable... ids) {
		genericDao.removeByIds(ids);
	}

	public boolean save(T entity) {
		return genericDao.save(entity);
	}

	public boolean[] save(T... entities) {
		return genericDao.save(entities);
	}

	public <RT> List<RT> search(ISearch search) {
		return genericDao.search(search);
	}

	public <RT> SearchResult<RT> searchAndCount(ISearch search) {
		return genericDao.searchAndCount(search);
	}

	public boolean equals(Object obj) {
		return genericDao.equals(obj);
	}

	public <RT> RT searchUnique(ISearch search) {
		return genericDao.searchUnique(search);
	}

	public Filter getFilterFromExample(T example) {
		return genericDao.getFilterFromExample(example);
	}

	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return genericDao.getFilterFromExample(example, options);
	}

}
