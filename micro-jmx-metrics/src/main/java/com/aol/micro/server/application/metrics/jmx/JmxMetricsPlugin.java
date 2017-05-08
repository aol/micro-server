package com.aol.micro.server.application.metrics.jmx;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PSetX;

public class JmxMetricsPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(JmxMetricsAcquirer.class);
    }

}
