package com.oath.micro.server.spring;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.spring.datasource.HikariCPConfig;
import com.oath.micro.server.spring.datasource.HikariCPDataSourceBuilder;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * @author kewang
 *
 */
public class HikariCPPlugin implements Plugin {

	@Override
	public Set<Class> springClasses() {
		return SetX.of(HikariCPConfig.class, HikariCPDataSourceBuilder.class);
	}

	

}
