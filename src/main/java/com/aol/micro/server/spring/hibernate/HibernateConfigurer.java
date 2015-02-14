package com.aol.micro.server.spring.hibernate;

import java.util.List;
import java.util.stream.Stream;

import com.aol.micro.server.Config;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class HibernateConfigurer {

	public static Config entityScan(String... packages){
		HibernateConfig.setPackages(ImmutableList.of("app.hibernate.com.aol.micro.server"));

		return Config.builder().build();
	}
	
	
	
}
