package com.aol.micro.server.application.registry.plugin;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.application.registry.ApplicationRegisterImpl;
import com.aol.micro.server.application.registry.Cleaner;
import com.aol.micro.server.application.registry.Finder;
import com.aol.micro.server.application.registry.HealthChecker;
import com.aol.micro.server.application.registry.Job;
import com.aol.micro.server.application.registry.Register;
import com.aol.micro.server.application.registry.RegisterConfig;
import com.aol.micro.server.application.registry.ServiceRegistryResource;
import com.aol.micro.server.application.registry.StatsChecker;

public class ApplicationRegistryPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(ApplicationRegisterImpl.class, Cleaner.class, Register.class, ServiceRegistryResource.class,
                        RegisterConfig.class, Job.class, Finder.class, HealthChecker.class, StatsChecker.class);
    }

}
