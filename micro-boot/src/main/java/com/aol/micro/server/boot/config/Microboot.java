package com.aol.micro.server.boot.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.stereotype.Component;

import com.aol.micro.server.config.Classes;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public @interface Microboot{

	
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
	 * @return Property file name 
	 * default value is 'application.properties'
	 */
	String propertiesName() default "application.properties";
	
	/**
	 * @return Hibernate entity scan packages
	 */
	String[] entityScan() default {};
	
	/**
	 * @return Properties to be injected into spring format is 
	 * key,value,key,value comma separated list
	 */
	String[] properties() default {};
	
	
}

