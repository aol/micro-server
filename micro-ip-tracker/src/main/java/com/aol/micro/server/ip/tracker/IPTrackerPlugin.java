package com.aol.micro.server.ip.tracker;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;


public class IPTrackerPlugin implements Plugin{

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(BeanConfiguration.class);
		
	}
	

	

}
