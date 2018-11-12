package com.oath.micro.server.logback;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.logback.rest.LogbackLoggerResource;
import com.oath.micro.server.logback.rest.LogbackRootLoggerResource;
import com.oath.micro.server.logback.service.LogbackRootLoggerChecker;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * @author Ke Wang
 *
 */
public class LogbackPlugin implements Plugin {
	@Override
	public Set<Class> springClasses() {
		return SetX.of(LogbackRootLoggerResource.class, LogbackRootLoggerChecker.class, LogbackLoggerResource.class);
	}

}
