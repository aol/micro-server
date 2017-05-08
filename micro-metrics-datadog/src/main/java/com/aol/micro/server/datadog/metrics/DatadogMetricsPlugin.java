package com.aol.micro.server.datadog.metrics;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

/**
 *
 * Collections of Spring configuration classes (Classes annotated with @Configuration)
 * that configure various useful pieces of functionality - such as property file loading,
 * datasources, scheduling etc
 *
 * @author arunbcodes
 */

public class DatadogMetricsPlugin  implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of( DatadogMetricsConfigurer.class);
    }
}
