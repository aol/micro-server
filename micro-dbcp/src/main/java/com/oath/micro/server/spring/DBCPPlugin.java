package com.oath.micro.server.spring;

import java.util.Set;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.spring.datasource.DBCPConfig;
import com.oath.micro.server.spring.datasource.DBCPDataSourceBuilder;
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
