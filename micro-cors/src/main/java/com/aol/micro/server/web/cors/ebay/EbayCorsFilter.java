package com.aol.micro.server.web.cors.ebay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.FilterConfiguration;

@Component
public class EbayCorsFilter implements FilterConfiguration {


	private final Map<String,String> initParameters;
	private final  boolean simple;
	private final String mapping;
	
	@Autowired(required=false)
	public EbayCorsFilter(@Value("${cors.simple:false}")boolean simple, 
								@Qualifier("ebay-cors-config" ) Map<String,String> initParameters,
								@Value("${cors.mapping:/*}")String mapping){
		this.simple=simple;
		this.initParameters = initParameters;
		this.mapping = mapping;
	}
	
	@Autowired(required=false)
	public EbayCorsFilter(@Value("${cors.simple:false}")boolean simple,@Value("${cors.mapping:/*}")String mapping){
		this.simple=simple;
		this.initParameters = new HashMap<>();
		this.mapping = mapping;
	}
	
	@Override
	public String[] getMapping() {
		if(!simple)
			return new String[] {mapping };
		else
			return new String[0];
	}

	public Class<? extends Filter> getFilter() {
		return org.ebaysf.web.cors.CORSFilter.class;
	}

	public String getName() {
		return "CORS Filter";
	}

	public Map<String,String> getInitParameters(){	
		return initParameters;

	}
}
