package com.aol.micro.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Wither;

import com.aol.micro.server.spring.datasource.DataSourceBuilder;
import com.aol.micro.server.spring.datasource.JdbcConfig;
import com.aol.micro.server.spring.datasource.hibernate.DAOProvider;
import com.aol.micro.server.spring.datasource.hibernate.GenericHibernateService;
import com.aol.micro.server.spring.datasource.hibernate.HibernateConfig;
import com.aol.micro.server.spring.datasource.hibernate.SpringDataConfig;
import com.aol.micro.server.spring.datasource.jdbc.JdbcTemplateConfig;
import com.aol.micro.server.spring.datasource.jdbc.SQL;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;



@AllArgsConstructor
@Getter
@Wither
public class Config {
	
	private final String defaultDataSourceName;
	private final ImmutableList<Class> classes;
	private final ImmutableMap<String,String> properties;
	
	private final String propertiesName;
	private final ImmutableMap<String,List<String>> dataSources;
	
	public Config() {
		classes = null;
		properties=  ImmutableMap.of();
		dataSources = ImmutableMap.of();
		defaultDataSourceName="db";
		propertiesName = "application.properties";
	}

	private static ThreadLocal<Config> instance= new ThreadLocal<>();
	
	public void set(){
		instance.set(this);
	}
	public static Config get(){
		if(instance.get() ==null)
			new Config().set();
		return instance.get();
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
		 List<Class> result = Lists.<Class>newArrayList(JdbcConfig.class ,
				 DataSourceBuilder.class);
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableList.copyOf(result));
	}

	
	public Config withJdbcClasses(Class... c){
		 List<Class> result = Lists.<Class>newArrayList(SpringDataConfig.class,JdbcConfig.class ,
				 DAOProvider.class,DataSourceBuilder.class,SQL.class,JdbcTemplateConfig.class);
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableList.copyOf(result));
	}
	
	public Config withHibernateClasses(Class... c){
		 List<Class> result = Lists.<Class>newArrayList(HibernateConfig.class,JdbcConfig.class ,
				 GenericHibernateService.class,DAOProvider.class,DataSourceBuilder.class,SQL.class);
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableList.copyOf(result));
	}
}
