package com.oath.micro.server.module;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;

import org.junit.Test;

public class EnvironmentTest {

	@Test
	public void testGetModuleBean() {
		
		
		
		Environment environment = new Environment(new Properties(),
				Arrays.asList(new ModuleBean(8081, "host1", () -> "test")));
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
	
	@Test
	public void testDefaultHost() throws UnknownHostException {
		String host =InetAddress.getLocalHost().getHostName();
		Environment environment = new Environment(new Properties());
		environment.assureModule(() ->"context");
		assertThat(environment.getModuleBean(()-> "context").getHost(), is(host));
	}
	@Test
	public void testDefaultHostNotNull() throws UnknownHostException {
		
		Environment environment = new Environment(new Properties());
		environment.assureModule(() ->"context");
		assertThat(environment.getModuleBean(()-> "context").getHost(), is(not(nullValue())));
	}
	@Test
	public void testHostOverride() throws UnknownHostException {
		
		Properties props = new Properties();
		props.put("context.host", "overriden-host");
		Environment environment = new Environment(props);
		environment.assureModule(() ->"context");
		assertThat(environment.getModuleBean(()-> "context").getHost(), is("overriden-host"));
	}
}
