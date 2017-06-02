package com.aol.micro.server.curator;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.curator.lock.CuratorDistributedLockServiceProvider;
import cyclops.collections.immutable.PersistentSetX;

public class CuratorPlugin implements Plugin {
	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(CuratorDistributedLockServiceProvider.class);
	}
}
