package com.aol.micro.server.mysql.distlock;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.mysql.distlock.datasource.JdbcConfigDistLock;
import com.aol.micro.server.mysql.distlock.datasource.MysqlDataSourceBuilder;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class MySqlPlugin implements Plugin {
	public Set<Class> springClasses(){
		return SetX.of(DistributedLockServiceMySqlImpl.class,
				MysqlDataSourceBuilder.class,JdbcConfigDistLock.class);
	}
}
