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



/**
 * 
 * Class for configuring a Spring Context for Microserver
 * 
 * @author johnmcclean
 *
 */
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
	/**
	 * Define the packages that hibernate should scan for Hibernate entities
	 * Should be used in conjunction Microserver Spring Configuration classes @See Classes#HIBERNATE_CLASSES
	 * 
	 * @param packages Packages to scan for hibernate entities
	 * @return New Config object, with configured packages
	 */
	public Config withEntityScan(String... packages){
		Map<String,List<String>> newMap = new HashMap<>(dataSources);
		newMap.put(defaultDataSourceName,Arrays.asList(packages));
		return this.withDataSources(ImmutableMap.copyOf(newMap));
	}
	
	
	/**
	 * Add the provided Classes to initial Spring Context as well as
	 *  @see Classes#DATASOURCE_CLASSES
	 * 
	 * @param c Array of additional Spring configuration classes
	 * @return New Config object, with configured packages
	 */
	public Config withDefaultDataSource(Class... c){
		 List<Class> result =Lists.newArrayList(Classes.DATASOURCE_CLASSES.getClasses());
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableSet.copyOf(result));
	}

	
	/**
	 * 
	 *  Add the provided Classes to initial Spring Context as well as
	 *  @see Classes#JDBC_CLASSES
	 * 
	 * @param c Array of additional Spring configuration classes
	 * @return New Config object, with configured packages
	 */
	public Config withJdbcClasses(Class... c){
		 List<Class> result = Lists.newArrayList(Classes.JDBC_CLASSES.getClasses());
		 result.addAll(Arrays.asList(Classes.SPRING_DATA_CLASSES.getClasses()));
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableSet.copyOf(result));
	}
	
	/**
	 * Add the provided Classes to initial Spring Context as well as
	 *  @see Classes#SPRING_DATA_CLASSES
	 * 
	 * @param c Array of additional Spring configuration classes
	 * @return New Config object, with configured packages
	 */
	public Config withHibernateClasses(Class... c){
		 Set<Class> result = Sets.newHashSet(Classes.HIBERNATE_CLASSES.getClasses());
		 result.addAll(Arrays.asList(Classes.SPRING_DATA_CLASSES.getClasses()));
		 if(classes!=null)
			 result.addAll(classes);
		 Stream.of(c).forEach(next -> result.add(next));
		 return this.withClasses(ImmutableSet.copyOf(result));
	}
	
}
