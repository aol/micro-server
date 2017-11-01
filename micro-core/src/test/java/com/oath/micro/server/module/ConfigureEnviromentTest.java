package com.oath.micro.server.module;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class ConfigureEnviromentTest {

	ConfigureEnviroment configureEnviroment = new ConfigureEnviroment();
	ModuleBean moduleBean;

	@Before
	public void setUp() {
		moduleBean = ModuleBean.builder().port(8080).host("host").module(() -> "simple").build();
	}

	@Test
	public void testEnvironmentNoModules() {
		Environment environment = configureEnviroment.environment(new Properties());
		assertThat(environment.getModuleBean(() -> "simple") == null, is(true));
	}

	@Test
	public void testEnvironment() {
		Environment environment = configureEnviroment.environment(new Properties());
		assertThat(environment.getModuleBean(() -> "simple") == null, is(true));
	}
}
