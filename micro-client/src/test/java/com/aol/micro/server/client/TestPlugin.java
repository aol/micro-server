package com.aol.micro.server.client;

import java.util.ArrayList;
import java.util.List;

import com.aol.micro.server.Plugin;

public class TestPlugin implements Plugin {
	public List<String> providers(){
		return new ArrayList<>();
	}
}
