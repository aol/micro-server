package com.aol.micro.server.spring;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.FunctionalModule;
import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.HibernateConfig;
import com.aol.micro.server.spring.datasource.hibernate.SpringDataConfig;
import com.aol.micro.server.spring.datasource.jdbc.SQL;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class DataFunctionalModule implements FunctionalModule{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(JdbcConfig.class,  
						DataSourceBuilder.class, SQL.class, 
						SpringDataConfig.class,
						HibernateConfig.class));
	}
}
