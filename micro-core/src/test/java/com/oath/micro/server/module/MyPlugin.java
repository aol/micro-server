package com.oath.micro.server.module;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.plugin.Bean1;
import cyclops.reactive.collections.mutable.ListX;
import cyclops.reactive.collections.mutable.SetX;

import java.util.List;
import java.util.Set;

public class MyPlugin implements Plugin{
	public List<String> providers(){
		return ListX.of("com.my.new.provider","com.my.new.provider2");
	}

    @Override
    public Set<Class> springClasses() {
        return SetX.of(Bean1.class);
    }
}
