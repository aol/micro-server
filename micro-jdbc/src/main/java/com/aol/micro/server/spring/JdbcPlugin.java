package com.aol.micro.server.spring;

import java.util.Optional;
import java.util.Set;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.jdbc.SQL;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

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
	public Set<Class> springClasses() {
		return SetX.of(JdbcConfig.class, SQL.class);
	}


}
