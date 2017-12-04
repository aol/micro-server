package com.oath.micro.server.application.registry.plugin;

import com.oath.micro.server.Plugin;
import com.oath.micro.server.application.registry.ApplicationRegisterImpl;
import com.oath.micro.server.application.registry.Cleaner;
import com.oath.micro.server.application.registry.Finder;
import com.oath.micro.server.application.registry.RegistryHealthChecker;
import com.oath.micro.server.application.registry.Job;
import com.oath.micro.server.application.registry.Register;
import com.oath.micro.server.application.registry.RegisterConfig;
import com.oath.micro.server.application.registry.ServiceRegistryResource;
import com.oath.micro.server.application.registry.RegistryStatsChecker;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class ApplicationRegistryPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(
            ApplicationRegisterImpl.class,
            Cleaner.class,
            Register.class,
            ServiceRegistryResource.class,
            RegisterConfig.class,
            Job.class,
            Finder.class,
            RegistryHealthChecker.class,
            RegistryStatsChecker.class
        );
    }

}
