package com.oath.micro.server.spring.boot;

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
public @interface MicroSpringBoot{
	
}