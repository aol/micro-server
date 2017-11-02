package com.oath.micro.server.log4j;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.log4j.rest.Log4jLoggerResource;
import com.oath.micro.server.log4j.rest.Log4jRootLoggerResource;
import com.oath.micro.server.log4j.service.Log4jRootLoggerChecker;
import cyclops.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * @author Ke Wang
 *
 */
public class Log4jPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return SetX.of(Log4jRootLoggerResource.class, Log4jLoggerResource.class, Log4jRootLoggerChecker.class);
	}

}
