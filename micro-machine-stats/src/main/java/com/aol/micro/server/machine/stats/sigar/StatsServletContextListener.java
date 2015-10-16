package com.aol.micro.server.machine.stats.sigar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StatsServletContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
