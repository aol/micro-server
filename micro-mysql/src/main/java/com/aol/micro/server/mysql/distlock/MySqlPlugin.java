package com.aol.micro.server.mysql.distlock;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.mysql.distlock.datasource.JdbcConfigDistLock;
import com.aol.micro.server.mysql.distlock.datasource.MysqlDataSourceBuilder;

public class MySqlPlugin implements Plugin {
	public PSetX<Class> springClasses(){
		return PSetX.of(DistributedLockServiceMySqlImpl.class,
				MysqlDataSourceBuilder.class,JdbcConfigDistLock.class);
	}
}
