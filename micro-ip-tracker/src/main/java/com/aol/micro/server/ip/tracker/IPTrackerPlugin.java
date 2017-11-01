package com.aol.micro.server.ip.tracker;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;


public class IPTrackerPlugin implements Plugin{

	@Override
	public Set<Class> springClasses() {
		return SetX.of(BeanConfiguration.class);
		
	}
	

	

}
