package com.aol.micro.server.logback;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.logback.rest.LogbackLoggerResource;
import com.aol.micro.server.logback.rest.LogbackRootLoggerResource;
import com.aol.micro.server.logback.service.LogbackRootLoggerChecker;

/**
 * 
 * @author Ke Wang
 *
 */
public class LogbackPlugin implements Plugin {
	@Override
	public PSetX<Class> springClasses() {
		return PSetX.of(LogbackRootLoggerResource.class, LogbackRootLoggerChecker.class, LogbackLoggerResource.class);
	}

}
