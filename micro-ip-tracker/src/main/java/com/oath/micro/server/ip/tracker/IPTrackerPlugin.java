package com.oath.micro.server.ip.tracker;

import com.oath.micro.server.Plugin;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;


public class IPTrackerPlugin implements Plugin{

	@Override
	public Set<Class> springClasses() {
		return SetX.of(BeanConfiguration.class);
		
	}
	

	

}
