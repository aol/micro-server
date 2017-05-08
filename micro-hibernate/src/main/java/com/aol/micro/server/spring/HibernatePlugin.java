package com.aol.micro.server.spring;

import java.util.Optional;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.HibernateConfig;
import com.aol.micro.server.spring.datasource.hibernate.SpringDataConfig;
import com.aol.micro.server.spring.datasource.jdbc.SQL;
import cyclops.collections.immutable.PSetX;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class HibernatePlugin implements Plugin {

	@Override
	public Optional<SpringDBConfig> springDbConfigurer() {
		return Optional.of(new HibernateSpringConfigurer());
	}

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(JdbcConfig.class, SQL.class, SpringDataConfig.class, HibernateConfig.class);
	}

	

}
