package com.aol.micro.server.ip.tracker;

import javax.servlet.Filter;

import cyclops.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.aol.micro.server.auto.discovery.FilterConfiguration;

@Configuration
public class BeanConfiguration {
	@Value("${load.balancer.ip.forwarding.header:X-LB-Client-IP}") 
	String ipForwardingHeaderValue;
	@Value("${ip.tracker.mappings:/*}") 
	String[] mappingsValue;
	
	@Bean
	public FilterConfiguration ipTracker(){
		return new FilterConfiguration(){

			@Override
			public String[] getMapping() {
				return mappingsValue;
			}

			@Override
			public Either<Class<? extends Filter>, Filter> getFilter() {
				return Either.right(new QueryIPRetriever(ipForwardingHeaderValue, mappingsValue));
			}
			
		};
	}
}
