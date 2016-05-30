package com.aol.micro.server.distlock;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

/**
 * 
 * @author Ke Wang
 *
 */
public class DistlockPlugin implements Plugin {
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(LockController.class);
	}

}
