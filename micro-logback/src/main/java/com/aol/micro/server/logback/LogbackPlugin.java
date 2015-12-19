package com.aol.micro.server.logback;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.logback.rest.LogbackLoggerResource;
import com.aol.micro.server.logback.rest.LogbackRootLoggerResource;

/**
 * 
 * @author Ke Wang
 *
 */
public class LogbackPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(LogbackRootLoggerResource.class, LogbackLoggerResource.class));
	}

}
