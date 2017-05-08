package com.aol.micro.server.mysql.distlock;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.mysql.distlock.datasource.JdbcConfigDistLock;
import com.aol.micro.server.mysql.distlock.datasource.MysqlDataSourceBuilder;
import cyclops.collections.immutable.PSetX;

public class MySqlPlugin implements Plugin {
	public PSetX<Class> springClasses(){
		return PSetX.of(DistributedLockServiceMySqlImpl.class,
				MysqlDataSourceBuilder.class,JdbcConfigDistLock.class);
	}
}
