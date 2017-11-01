package com.aol.micro.server.application.metrics.jmx;

import com.aol.micro.server.Plugin;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class JmxMetricsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(JmxMetricsAcquirer.class);
    }

}
