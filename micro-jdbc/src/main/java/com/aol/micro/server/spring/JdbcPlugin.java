package com.aol.micro.server.spring;

import java.util.Optional;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.spring.datasource.JdbcConfig;
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
public class JdbcPlugin implements Plugin {

	@Override
	public Optional<SpringDBConfig> springDbConfigurer() {
		return Optional.of(new SpringConfigurer());
	}

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(JdbcConfig.class, SQL.class);
	}


}
