package com.aol.micro.server.ip.tracker;

import java.util.Map;
import java.util.function.Function;

import javax.servlet.Filter;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.servers.model.ServerData;
import com.aol.micro.server.utility.HashMapBuilder;




public class IPTrackerPlugin implements Plugin{
	
	@Override
	public Function<ServerData,Map<String,Filter>> filters(){
		return serverData -> HashMapBuilder.of("/*",new QueryIPRetriever());
	}
	

	

}
