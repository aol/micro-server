package com.aol.micro.server.spring.datasource.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.aol.micro.server.Config;
import com.aol.micro.server.spring.datasource.JdbcConfig;

@Configuration
@EnableTransactionManagement
public class SpringDataConfig {

	@Autowired
	private JdbcConfig env;
	@Autowired
	private DataSource dataSource;	
	
	@Bean
	  public EntityManagerFactory entityManagerFactory() {

	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan(Config.get().getDataSources().get(Config.get().getDefaultDataSourceName()).toArray(new String[0]));
	    factory.setDataSource(dataSource);
	    factory.afterPropertiesSet();

	    return factory.getObject();
	  }

	  @Bean
	  public PlatformTransactionManager transactionManager() {

	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory());
	    return txManager;
	  }
}
