package com.aol.micro.server.spring;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.spring.datasource.HikariCPConfig;
import com.aol.micro.server.spring.datasource.HikariCPDataSourceBuilder;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

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
