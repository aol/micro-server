package com.oath.micro.server.spring.datasource.hibernate;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.oath.micro.server.config.ConfigAccessor;
import com.oath.micro.server.spring.datasource.JdbcConfig;

import java.util.Arrays;

@Configuration
@EnableTransactionManagement
public class SpringDataConfig {

	@Resource(name = "mainEnv")
	private JdbcConfig env;
	@Resource(name = "mainDataSource")
	private DataSource dataSource;

	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		if (env.getGenerateDdl().equals("true")) {
			vendorAdapter.setGenerateDdl(true);
		} else {
			vendorAdapter.setGenerateDdl(false);
		}

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);

		factory.setPackagesToScan(new ConfigAccessor().get().getDataSources()
                .getOrElse(new ConfigAccessor().get().getDefaultDataSourceName(), Arrays.asList())
				.toArray(new String[0]));
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
