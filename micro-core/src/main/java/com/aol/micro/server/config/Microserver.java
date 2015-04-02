package com.aol.micro.server.config;

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

	/**
	 * @return Preconfigured collections of classes to be passed to initial Spring context
	 * They  configure various useful pieces of functionality - such as property file loading,
	 * datasources, scheduling etc
	 */
	Classes[] springClasses() default {};

	/**
	 * @return Property file name
	 */
	String propertiesName() default "application.properties";

	/**
	 * @return Instance property file name
	 */
	String instancePropertiesName() default "instance.properties";

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
