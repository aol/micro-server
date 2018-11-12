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

public class MicroserverEnvironmentTest {

	@Test
	public void testGetModuleBean() {
		
		
		
		MicroserverEnvironment microserverEnvironment = new MicroserverEnvironment(new Properties(),
				Arrays.asList(new ModuleBean(8081, "host1", () -> "test")));
		assertThat(microserverEnvironment.getModuleBean(()-> "test").getPort(), is(8081));
	}
	@Test
	public void testDefaultPort() {
		MicroserverEnvironment microserverEnvironment = new MicroserverEnvironment(new Properties());
		microserverEnvironment.assureModule(() ->"context");
		assertThat(microserverEnvironment.getModuleBean(()-> "context").getPort(), is(8080));
	}
	
	@Test
	public void testGetModuleBeanOverridePort() {
		Properties props = new Properties();
		props.put("context.port", 8081);
		MicroserverEnvironment microserverEnvironment = new MicroserverEnvironment(props);
		microserverEnvironment.assureModule(() ->"context");
		assertThat(microserverEnvironment.getModuleBean(()-> "context").getPort(), is(8081));
	}
	
	@Test
	public void testDefaultHost() throws UnknownHostException {
		String host =InetAddress.getLocalHost().getHostName();
		MicroserverEnvironment microserverEnvironment = new MicroserverEnvironment(new Properties());
		microserverEnvironment.assureModule(() ->"context");
		assertThat(microserverEnvironment.getModuleBean(()-> "context").getHost(), is(host));
	}
	@Test
	public void testDefaultHostNotNull() throws UnknownHostException {
		
		MicroserverEnvironment microserverEnvironment = new MicroserverEnvironment(new Properties());
		microserverEnvironment.assureModule(() ->"context");
		assertThat(microserverEnvironment.getModuleBean(()-> "context").getHost(), is(not(nullValue())));
	}
	@Test
	public void testHostOverride() throws UnknownHostException {
		
		Properties props = new Properties();
		props.put("context.host", "overriden-host");
		MicroserverEnvironment microserverEnvironment = new MicroserverEnvironment(props);
		microserverEnvironment.assureModule(() ->"context");
		assertThat(microserverEnvironment.getModuleBean(()-> "context").getHost(), is("overriden-host"));
	}
}
