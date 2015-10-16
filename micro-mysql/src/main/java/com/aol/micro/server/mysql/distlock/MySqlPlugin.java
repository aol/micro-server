package com.aol.micro.server.mysql.distlock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;

public class MySqlPlugin implements Plugin {
	public Set<Class> springClasses(){
		return new HashSet(Arrays.asList(DistributedLockServiceMySqlImpl.class));
	}
}
