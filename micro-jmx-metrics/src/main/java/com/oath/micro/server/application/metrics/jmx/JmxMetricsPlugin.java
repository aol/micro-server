package com.oath.micro.server.application.metrics.jmx;

import com.oath.micro.server.Plugin;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class JmxMetricsPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(JmxMetricsAcquirer.class);
    }

}
