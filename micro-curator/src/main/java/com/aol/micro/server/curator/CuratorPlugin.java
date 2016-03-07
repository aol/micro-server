package com.aol.micro.server.curator;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.curator.lock.CuratorDistributedLockServiceProvider;

public class CuratorPlugin implements Plugin {
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(CuratorDistributedLockServiceProvider.class);
	}
}
