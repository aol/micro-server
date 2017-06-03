package com.aol.micro.server.logback;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.logback.rest.LogbackLoggerResource;
import com.aol.micro.server.logback.rest.LogbackRootLoggerResource;
import com.aol.micro.server.logback.service.LogbackRootLoggerChecker;
import cyclops.collections.immutable.PersistentSetX;

/**
 * 
 * @author Ke Wang
 *
 */
public class LogbackPlugin implements Plugin {
	@Override
	public PersistentSetX<Class> springClasses() {
		return PersistentSetX.of(LogbackRootLoggerResource.class, LogbackRootLoggerChecker.class, LogbackLoggerResource.class);
	}

}
