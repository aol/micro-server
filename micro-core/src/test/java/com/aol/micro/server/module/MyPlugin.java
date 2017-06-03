package com.aol.micro.server.module;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.LinkedListX;

public class MyPlugin implements Plugin{
	public LinkedListX<String> providers(){
		return LinkedListX.of("com.my.new.provider","com.my.new.provider2");
	}
}