package com.aol.micro.server.machine.stats.sigar.plugin;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.machine.stats.sigar.MachineStatsChecker;
import com.aol.micro.server.machine.stats.sigar.SigarStats;
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
public class MachineStatsPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(StatsResource.class, MachineStatsChecker.class, StatsServletContextListener.class,
                        SigarStats.class);
    }

}
