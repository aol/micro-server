package com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;

import com.google.common.collect.Lists;

public class EnvironmentTest {

	@Test
	public void testGetModuleBean() {
		Environment environment = new Environment(new Properties(),
				Lists.newArrayList(new ModuleBean(8081, "host1", () -> "test")));
		assertThat(environment.getModuleBean(()-> "test").getPort(), is(8081));
	}
	@Test
	public void testDefaultPort() {
		Environment environment = new Environment(new Properties());
		environment.assureModule(() ->"context");
		assertThat(environment.getModuleBean(()-> "context").getPort(), is(8080));
	}
	
	@Test
	public void testGetModuleBeanOverridePort() {
		Properties props = new Properties();
		props.put("context.port", 8081);
		Environment environment = new Environment(props);
		environment.assureModule(() ->"context");
		assertThat(environment.getModuleBean(()-> "context").getPort(), is(8081));
	}
}
