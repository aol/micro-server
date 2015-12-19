package com.aol.micro.server.log4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.log4j.rest.Log4jLoggerResource;
import com.aol.micro.server.log4j.rest.Log4jRootLoggerResource;
import com.aol.micro.server.log4j.service.Log4jRootLoggerChecker;

/**
 * 
 * @author Ke Wang
 *
 */
public class Log4jPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(Log4jRootLoggerResource.class, Log4jLoggerResource.class, Log4jRootLoggerChecker.class));
	}

}
