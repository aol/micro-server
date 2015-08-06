package com.aol.micro.server.module;

import java.util.Arrays;
import java.util.List;

import com.aol.micro.server.Plugin;

public class MyPlugin implements Plugin{
	public List<String> providers(){
		return Arrays.asList("com.my.new.provider","com.my.new.provider2");
	}
}