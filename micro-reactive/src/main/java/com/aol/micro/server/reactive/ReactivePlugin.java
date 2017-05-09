package com.aol.micro.server.reactive;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;


public class ReactivePlugin implements Plugin{

	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(ResponderConfigurer.class);
	}
	
	
	
}
