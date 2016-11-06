package com.aol.micro.server.machine.stats.sigar;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aol.cyclops.util.ExceptionSoftener;

import kamon.sigar.SigarProvisioner;

public class StatsServletContextListener implements ServletContextListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${machine.stats.deploy.dir:#{null}}")
    String deployDir;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String workingDir = deployDir == null ? System.getProperty("user.dir") : deployDir;
        String destination = workingDir + "/sigar-lib";

        System.setProperty("java.library.path", destination);
        logger.info("java.library.path is {}", destination);
        if (!new File(
                      System.getProperty("java.library.path")).exists()) {
            final File location = new File(
                                           System.getProperty("java.library.path"));
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
