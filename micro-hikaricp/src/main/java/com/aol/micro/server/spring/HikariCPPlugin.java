package com.aol.micro.server.spring;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.spring.datasource.HikariCPConfig;
import com.aol.micro.server.spring.datasource.HikariCPDataSourceBuilder;
import cyclops.collections.immutable.PersistentSetX;

/**
 * 
 * @author kewang
 *
 */
public class HikariCPPlugin implements Plugin {

	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(HikariCPConfig.class, HikariCPDataSourceBuilder.class);
	}

	

}
