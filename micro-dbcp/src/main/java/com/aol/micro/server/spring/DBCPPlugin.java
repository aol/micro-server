package com.aol.micro.server.spring;

import java.util.Set;
import java.util.function.Function;

import javax.servlet.ServletContextListener;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.spring.datasource.DBCPConfig;
import com.aol.micro.server.spring.datasource.DBCPDataSourceBuilder;

/**
 * 
 * @author kewang
 *
 */
public class DBCPPlugin implements Plugin {

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(DBCPConfig.class, DBCPDataSourceBuilder.class);
	}

	

}
