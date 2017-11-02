package com.oath.micro.server.curator;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.curator.lock.CuratorDistributedLockServiceProvider;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class CuratorPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return SetX.of(CuratorDistributedLockServiceProvider.class);
	}
}
