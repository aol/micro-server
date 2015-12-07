package com.aol.micro.server.jackson;

import java.util.HashSet;
import java.util.Set;

import com.aol.micro.server.Plugin;
import com.aol.micro.server.rest.jackson.JacksonFeature;

public class JacksonPlugin implements Plugin {

	@Override
	public Set<Class> jaxRsResources() {

		Set<Class> set = new HashSet<>();
		set.add(JacksonFeature.class);
		return set;
	}

	@Override
	public Set<Class> springClasses() {

		Set<Class> set = new HashSet<>();
		set.add(CoreJacksonConfigurator.class);
		set.add(JacksonConfigurers.class);
		return set;
	}

}
