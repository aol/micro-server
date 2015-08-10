package com.aol.micro.server.machine.stats.sigar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.aol.advertising.lana.common.loggers.FunctionalAreaPrefix;
import com.aol.advertising.lana.common.loggers.LanaLogger;

public class StatsServletContextListener implements ServletContextListener {

	private final LanaLogger logger = LanaLogger.getLogger(FunctionalAreaPrefix.SERVER_START, this.getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String workingDir = System.getProperty("user.dir");
		String destination = workingDir + "/sigar-lib";
		logger.info("java.library.path is {}", destination);
		System.setProperty("java.library.path", destination);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
