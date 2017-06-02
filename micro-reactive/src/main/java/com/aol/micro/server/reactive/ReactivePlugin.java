package com.aol.micro.server.reactive;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;


public class ReactivePlugin implements Plugin{

	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(ResponderConfigurer.class);
	}
	
	
	
}
