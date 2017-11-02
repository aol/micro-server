package com.oath.micro.server.mysql.distlock;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.mysql.distlock.datasource.JdbcConfigDistLock;
import com.oath.micro.server.mysql.distlock.datasource.MysqlDataSourceBuilder;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class MySqlPlugin implements Plugin {
	public Set<Class> springClasses(){
		return SetX.of(DistributedLockServiceMySqlImpl.class,
				MysqlDataSourceBuilder.class,JdbcConfigDistLock.class);
	}
}
