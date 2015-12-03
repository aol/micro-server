package com.aol.micro.server.ip.tracker;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.Filter;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.utility.HashMapBuilder;




public class IPTrackerPlugin implements Plugin{

	@Override
	public Set<Class> springClasses() {
		Set<Class> classes = new HashSet<>();
		classes.add(QueryIPRetriever.class);
		return classes;
	}
	

	

}
