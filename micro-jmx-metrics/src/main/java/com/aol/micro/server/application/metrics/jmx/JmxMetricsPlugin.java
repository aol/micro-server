package com.aol.micro.server.application.metrics.jmx;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;

public class JmxMetricsPlugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(JmxMetricsAcquirer.class);
    }

}
