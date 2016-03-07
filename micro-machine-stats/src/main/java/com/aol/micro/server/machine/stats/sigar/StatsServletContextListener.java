package com.aol.micro.server.machine.stats.sigar;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kamon.sigar.SigarProvisioner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aol.cyclops.util.ExceptionSoftener;


public class StatsServletContextListener implements ServletContextListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
			String workingDir = System.getProperty("user.dir");
			String destination = workingDir + "/sigar-lib";
			logger.info("java.library.path is {}", destination);
			System.setProperty("java.library.path", destination);
		
		if(!new File(System.getProperty("java.library.path")).exists()){
		 final File location = new File(System.getProperty("java.library.path"));
	        try {
				SigarProvisioner.provision(location);
			} catch (Exception e) {
				throw ExceptionSoftener.throwSoftenedException(e);
			}
	       
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
