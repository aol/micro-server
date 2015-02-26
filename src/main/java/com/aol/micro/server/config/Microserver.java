package com.aol.micro.server.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.stereotype.Component;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
public @interface Microserver {

	
	String[] basePackages() default {};
	Class[] classes() default {};
	Classes[] springClasses() default {};
	String propertiesName() default "application.properties";
	String[] entityScan() default {};
	String[] properties() default {};
	boolean isSpringBoot() default false;
}
