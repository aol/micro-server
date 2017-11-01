package com.aol.micro.server.spring;

import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.spring.datasource.DBCPConfig;
import com.aol.micro.server.spring.datasource.DBCPDataSourceBuilder;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

/**
 * 
 * @author kewang
 *
 */
public class DBCPPlugin implements Plugin {

	@Override
	public Set<Class> springClasses() {
		return SetX.of(DBCPConfig.class, DBCPDataSourceBuilder.class);
	}

	

}
