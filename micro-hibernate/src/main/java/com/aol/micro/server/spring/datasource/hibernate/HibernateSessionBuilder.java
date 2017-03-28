package com.aol.micro.server.spring.datasource.hibernate;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.aol.cyclops.util.ExceptionSoftener;
import com.aol.micro.server.spring.datasource.JdbcConfig;

@Builder
@AllArgsConstructor
public class HibernateSessionBuilder {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final JdbcConfig env;
	private final DataSource dataSource;
	private final List<String> packages;

	public SessionFactory sessionFactory() {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

		sessionFactoryBean.setDataSource(dataSource);

		List<String> packagesToScan = packages;
		sessionFactoryBean.setPackagesToScan(packagesToScan.toArray(new String[packagesToScan.size()]));

		Properties p = new Properties();
		p.setProperty("hibernate.dialect", env.getDialect());
		if (env.getShowSql() != null)
			p.setProperty("hibernate.show_sql", env.getShowSql());

		if (env.getDdlAuto() != null)
			p.setProperty("hibernate.hbm2ddl.auto", env.getDdlAuto());

		if(env.getInitializationFile() != null) {
			p.setProperty("hibernate.hbm2ddl.import_files", env.getInitializationFile());

		}

		logger.info("Hibernate properties [  hibernate.dialect : {} ; hibernate.hbm2ddl.auto : {} ]", env.getDialect(), env.getDdlAuto());

		sessionFactoryBean.setHibernateProperties(p);

		try {
			sessionFactoryBean.afterPropertiesSet();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ExceptionSoftener.throwSoftenedException(e);
		}

		try {
			return sessionFactoryBean.getObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ExceptionSoftener.throwSoftenedException(e);
		}
		return null;
	}

	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
		return transactionManager;
	}
}
