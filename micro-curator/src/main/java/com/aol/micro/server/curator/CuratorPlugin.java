package com.aol.micro.server.curator;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.curator.lock.CuratorDistributedLockServiceProvider;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class CuratorPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return SetX.of(CuratorDistributedLockServiceProvider.class);
	}
}
