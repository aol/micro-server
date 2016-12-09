package com.aol.micro.server.application.metrics.jmx;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;

public class JmxMetricsPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(JmxMetricsAcquirer.class);
    }

}
