package com.aol.micro.server.module;

import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.micro.server.Plugin;

public class MyPlugin implements Plugin{
	public PStackX<String> providers(){
		return PStackX.of("com.my.new.provider","com.my.new.provider2");
	}
}