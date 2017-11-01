package com.oath.micro.server.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nonautoscan.com.aol.micro.server.AopConfig;
import nonautoscan.com.aol.micro.server.SSLConfig;
import nonautoscan.com.aol.micro.server.ScheduleAndAsyncConfig;

import com.oath.micro.server.module.ConfigureEnviroment;
import com.oath.micro.server.servers.AccessLogConfig;
import com.oath.micro.server.spring.properties.PropertyFileConfig;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class Classes {

	/**
	 * CORE CLASSES are the Core Microserver Spring Configuration classes
	 * Property support, Guava Event Bus, Spring AOP &amp; Scheduling
	 * Codahale Metrics, Event tracking etc
	 */
	public static final  Classes CORE_CLASSES = new Classes(PropertyFileConfig.class, AopConfig.class,
			 ScheduleAndAsyncConfig.class,  ConfigureEnviroment.class, AccessLogConfig.class, SSLConfig.class);
	
	
	@Getter
	private final Class[] classes;

	public Classes(Class... classes) {
		this.classes = classes;
	}

	public Classes combine(Classes c){
		List<Class> c1 = new ArrayList<>();
		for(Class next : classes)
			c1.add(next);
		for(Class next : c.classes)
			c1.add(next);
		return new Classes(c1.toArray(new Class[0]));
	}
}
