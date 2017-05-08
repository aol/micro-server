package com.aol.micro.server.web.cors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.Filter;

import cyclops.control.Xor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.aol.micro.server.auto.discovery.FilterConfiguration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class ConfigureBeans {
	
	@Value("${cors.simple:false}")
	private  boolean simple = false;
	@Value("${cors.mapping:/*}")
	private  String mapping;
	@Qualifier("ebay-cors-config" )
	@Autowired(required=false)
	private  Map<String,String> initParameters;
	
	@Bean
	public FilterConfiguration crossDomain(){
		return new FilterConfiguration() {
			
			@Override
			public String getName() {
				return "simple-cors";
			}

			@Override
			public String[] getMapping() {
				if(simple)
					return new String[] {mapping };
				else
					return new String[0];
			}
			@Override
			public Map<String, String> getInitParameters() {
				return Optional.ofNullable(initParameters).orElse(new HashMap<>());
			}
			@Override
			public Xor<Class<? extends Filter>, Filter> getFilter() {
				return Xor.secondary(CrossDomainFilter.class);
			}
		};
	}
	@Bean
	public FilterConfiguration ebayCrossDomain(){
		return new FilterConfiguration() {
			@Override
			public String getName() {
				return "ebay-cors";
			}
			@Override
			public String[] getMapping() {
				if(!simple)
					return new String[] {mapping };
				else
					return new String[0];
			}
			
			@Override
			public Map<String, String> getInitParameters() {
				return Optional.ofNullable(initParameters).orElse(new HashMap<>());
			}
			@Override
			public Xor<Class<? extends Filter>, Filter> getFilter() {
				return Xor.secondary( org.ebaysf.web.cors.CORSFilter.class);
			}
		};
	}
}
