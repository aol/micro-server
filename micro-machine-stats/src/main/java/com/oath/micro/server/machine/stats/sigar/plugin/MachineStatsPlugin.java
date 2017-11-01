package com.oath.micro.server.machine.stats.sigar.plugin;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.machine.stats.sigar.MachineStatsChecker;
import com.oath.micro.server.machine.stats.sigar.SigarStats;
import com.oath.micro.server.machine.stats.sigar.StatsServletContextListener;
import com.oath.micro.server.machine.stats.sigar.rest.StatsResource;
import cyclops.collections.mutable.SetX;

import java.util.Set;

/**
 * 
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 * 
 * @author johnmcclean
 *
 */
public class MachineStatsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(StatsResource.class, MachineStatsChecker.class, StatsServletContextListener.class,
                        SigarStats.class);
    }

}
