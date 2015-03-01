package com.aol.micro.server.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;



@AllArgsConstructor
@Getter
@Wither
public class Config {
	
	private final String defaultDataSourceName;
	private final ImmutableSet<Class> classes;
	private final ImmutableMap<String,String> properties;
	
	private final String propertiesName;
	private final ImmutableMap<String,List<String>> dataSources;
	private final SSLProperties sslProperties;
	
	
	public Config() {
		classes = ImmutableSet.of();
		properties=  ImmutableMap.of();
		dataSources = ImmutableMap.of();
		defaultDataSourceName="db";
		propertiesName = "application.properties";
		sslProperties = null;
	}

	private static volatile Config instance = null;
	
	public Config set(){
		instance = this;
		return this;
	}
	public static Config instance(){
		instance = new Config();
		return instance;
	}
	static Config get(){
		return instance;
		
	}
	public static void reset() {
		instance =null;
		
	}
	
	public Config withEntityScanDataSource(String dataSource,String... packages){
		Map<String,List<String>> newMap = new HashMap<>(dataSources);
		newMap.put(dataSource,Arrays.asList(packages));
		return this.withDataSources(ImmutableMap.copyOf(newMap));
	}
	public Config withEntityScan(String... packages){
		Map<String,List<String>> newMap = new HashMap<>(dataSources);
		newMap.put(defaultDataSourceName,Arrays.asList(packages));
		return this.withDataSources(ImmutableMap.copyOf(newMap));
	}
	
	
	public Config withDefaultDataSource(Class... c){
		 List<Class> result =Lists.newArrayList(Classes.DATASOURCE_CLASSES.getClasses());
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableSet.copyOf(result));
	}

	
	public Config withJdbcClasses(Class... c){
		 List<Class> result = Lists.newArrayList(Classes.JDBC_CLASSES.getClasses());
		 result.addAll(Arrays.asList(Classes.SPRING_DATA_CLASSES.getClasses()));
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableSet.copyOf(result));
	}
	
	public Config withHibernateClasses(Class... c){
		 Set<Class> result = Sets.newHashSet(Classes.HIBERNATE_CLASSES.getClasses());
		 result.addAll(Arrays.asList(Classes.SPRING_DATA_CLASSES.getClasses()));
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableSet.copyOf(result));
	}
	
}
