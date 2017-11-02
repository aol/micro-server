package com.oath.micro.server.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Microserver {

	/**
	 * @return Spring auto-scan base packages
	 */
	String[] basePackages() default {};

	/**
	 * @return Classes to be passed to initial Spring context
	 * e.g. Classes that have Spring @Configuration or @Component annotation
	 * 
	 */
	Class[] classes() default {};

	/** @return blacklist of classes that should be excluded from spring context
	 * 
	 */
	Class[] blacklistedClasses() default {};

	/**
	 * @return Property file name
	 */
	String propertiesName() default "application.properties";

	/**
	 * @return Instance property file name
	 */
	String instancePropertiesName() default "instance.properties";
	
	/**
	 * @return Filename for service type related properties (e.g. OrderService)
	 *
	 */
	String serviceTypePropertiesName() default "service-type.properties";

	/**
	 * @return Hibernate entity scan packages
	 */
	String[] entityScan() default {};

	/**
	 * @return Properties to be injected into spring format is 
	 * key,value,key,value comma separated list
	 */
	String[] properties() default {};

	/**
	 * @return true if the spring context should allow circular dependencies
	 * We recommend not to allow circular dependencies
	 */
	boolean allowCircularDependencies() default false;

	@Microserver
	static class Instance{}
}
