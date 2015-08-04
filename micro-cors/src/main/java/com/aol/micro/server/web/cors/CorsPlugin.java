package com.aol.micro.server.web.cors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.servlet.Filter;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.utility.HashMapBuilder;




public class CorsPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses(){
		return new HashSet<>(Arrays.asList(CrossDomainFilter.class));
	}
	

	

}
