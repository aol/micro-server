package com.aol.micro.server.module;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PStackX;

public class MyPlugin implements Plugin{
	public PStackX<String> providers(){
		return PStackX.of("com.my.new.provider","com.my.new.provider2");
	}
}