package com.aol.micro.server.module;


import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.LinkedListX;
import cyclops.collections.mutable.ListX;

import java.util.List;

public class MyPlugin implements Plugin{
	public List<String> providers(){
		return ListX.of("com.my.new.provider","com.my.new.provider2");
	}
}