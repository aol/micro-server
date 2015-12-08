package com.aol.micro.server.machine.stats.sigar.plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.machine.stats.sigar.MachineStatsChecker;
import com.aol.micro.server.machine.stats.sigar.StatsServletContextListener;
import com.aol.micro.server.machine.stats.sigar.rest.StatsResource;


/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class MachineStatsPlugin implements Plugin{
	
	@Override
	public Set<Class> springClasses() {
		return new HashSet<>(Arrays.asList(
				StatsResource.class, MachineStatsChecker.class
				,StatsServletContextListener.class));
	}

	

}
