package com.aol.micro.server.spring.hibernate;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.collect.Lists;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	private final Logger logger = LoggerFactory.getLogger( getClass());

	@Setter @Getter(AccessLevel.PACKAGE)
	private static volatile List<String> packages = Lists.newArrayList();
	@Resource
	private JdbcConfig env;

	@Bean(destroyMethod = "close", name = "dataSource")
	public DataSource dataSource() {
		return getDataSource();
	}

	@Bean(name = "sessionFactory")
	public SessionFactory sessionFactory() {
		
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();

		sessionFactoryBean.setDataSource(dataSource());

		List<String> packagesToScan = packages;
		sessionFactoryBean.setPackagesToScan(packagesToScan
				.toArray(new String[packagesToScan.size()]));

		Properties p = new Properties();
		p.setProperty("hibernate.dialect", env.getDialect());
		p.setProperty("hibernate.show_sql", env.getShowSql());

		if (env.getDdlAuto() != null)
			p.setProperty("hibernate.hbm2ddl.auto", env.getDdlAuto());

		logger.info(
				"Hibernate properties [  hibernate.dialect : {} ; hibernate.hbm2ddl.auto : {} ]",
				env.getDialect(), env.getDdlAuto());

		sessionFactoryBean.setHibernateProperties(p);

		try {
			sessionFactoryBean.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try{
			return sessionFactoryBean.getObject();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	
	

	private void retrySetup(BasicDataSource ds, int maxActive) {
		if (!"org.hibernate.dialect.HSQLDialect".equals(env.getDialect())) {
			ds.setTestOnBorrow(true);
			ds.setValidationQuery("SELECT 1");
			ds.setMaxActive(maxActive);
			ds.setMinEvictableIdleTimeMillis(1800000);
			ds.setTimeBetweenEvictionRunsMillis(1800000);
			ds.setNumTestsPerEvictionRun(3);
			ds.setTestWhileIdle(true);
			ds.setTestOnReturn(true);
		}
	}

	
	

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory());
		return transactionManager;
	}

	private DataSource getDataSource() {
		BasicDataSource ds = new BasicDataSource();

		ds.setDriverClassName(env.getDriverClassName());
		ds.setUrl(env.getUrl());
		ds.setUsername(env.getUsername());
		ds.setPassword(env.getPassword());
		retrySetup(ds, -1);

		return ds;
	}

}
