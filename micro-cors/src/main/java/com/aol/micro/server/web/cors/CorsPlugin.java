package com.aol.micro.server.web.cors;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.web.cors.ebay.EbayCorsFilter;




public class CorsPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses(){
		return new HashSet<>(Arrays.asList(CrossDomainFilter.class,EbayCorsFilter.class));
	}
	

	

}
