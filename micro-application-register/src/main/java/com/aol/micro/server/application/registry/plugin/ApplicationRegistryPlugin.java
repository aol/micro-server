package com.aol.micro.server.application.registry.plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.application.registry.ApplicationRegisterImpl;
import com.aol.micro.server.application.registry.Cleaner;
import com.aol.micro.server.application.registry.Finder;
import com.aol.micro.server.application.registry.Job;
import com.aol.micro.server.application.registry.Register;
import com.aol.micro.server.application.registry.RegisterConfig;
import com.aol.micro.server.application.registry.ServiceRegistryResource;

public class ApplicationRegistryPlugin implements Plugin {

	@Override
	public Set<Class> springClasses() {
		return new HashSet<Class>(Arrays.asList(ApplicationRegisterImpl.class,
				Cleaner.class,Register.class, ServiceRegistryResource.class,
				RegisterConfig.class,Job.class, Finder.class));
	}

}
