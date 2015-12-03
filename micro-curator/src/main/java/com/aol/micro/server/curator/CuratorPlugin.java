package com.aol.micro.server.curator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.curator.lock.CuratorDistributorLockServiceProvider;

public class CuratorPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(CuratorDistributorLockServiceProvider.class));
	}
}
