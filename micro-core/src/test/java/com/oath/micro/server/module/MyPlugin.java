package com.oath.micro.server.module;


import com.oath.micro.server.Plugin;
import cyclops.reactive.collections.mutable.ListX;

import java.util.List;

public class MyPlugin implements Plugin{
	public List<String> providers(){
		return ListX.of("com.my.new.provider","com.my.new.provider2");
	}
}
