package com.aol.micro.server.application.registry.plugin;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.application.registry.ApplicationRegisterImpl;
import com.aol.micro.server.application.registry.Cleaner;
import com.aol.micro.server.application.registry.Finder;
import com.aol.micro.server.application.registry.RegistryHealthChecker;
import com.aol.micro.server.application.registry.Job;
import com.aol.micro.server.application.registry.Register;
import com.aol.micro.server.application.registry.RegisterConfig;
import com.aol.micro.server.application.registry.ServiceRegistryResource;
import com.aol.micro.server.application.registry.RegistryStatsChecker;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class ApplicationRegistryPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ApplicationRegisterImpl.class, Cleaner.class, Register.class, ServiceRegistryResource.class,
                        RegisterConfig.class, Job.class, Finder.class, RegistryHealthChecker.class, RegistryStatsChecker.class);
    }

}
