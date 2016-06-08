package com.aol.micro.server.ip.tracker;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aol.cyclops.control.Xor;
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
			public Xor<Class<? extends Filter>, Filter> getFilter() {
				return Xor.primary(new QueryIPRetriever(ipForwardingHeaderValue, mappingsValue));
			}
			
		};
	}
}
