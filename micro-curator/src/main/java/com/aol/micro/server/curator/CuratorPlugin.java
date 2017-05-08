package com.aol.micro.server.curator;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.curator.lock.CuratorDistributedLockServiceProvider;
import cyclops.collections.immutable.PSetX;

public class CuratorPlugin implements Plugin {
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(CuratorDistributedLockServiceProvider.class);
	}
}
