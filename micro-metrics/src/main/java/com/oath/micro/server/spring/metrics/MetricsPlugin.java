package com.oath.micro.server.spring.metrics;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.spring.metrics.health.HealthCheckRunner;
import com.oath.micro.server.spring.metrics.health.HealthResource;
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
public class MetricsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(CodahaleMetricsConfigurer.class, HealthCheckRunner.class, HealthResource.class);
    }

}
