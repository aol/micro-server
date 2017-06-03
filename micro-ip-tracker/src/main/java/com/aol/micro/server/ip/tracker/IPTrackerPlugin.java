package com.aol.micro.server.ip.tracker;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;


public class IPTrackerPlugin implements Plugin{

	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(BeanConfiguration.class);
		
	}
	

	

}
