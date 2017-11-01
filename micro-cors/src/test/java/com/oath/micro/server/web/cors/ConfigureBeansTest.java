package com.oath.micro.server.web.cors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.assertThat;

public class ConfigureBeansTest {

	@Test
	public void testCrossDomainOn() {
		ConfigureBeans config = new ConfigureBeans(true,"/*",new HashMap<>());
		assertThat(config.crossDomain().getMapping().length,equalTo(1));
	}
	@Test
	public void testCrossDomainOff() {
		ConfigureBeans config = new ConfigureBeans(false,"/*",new HashMap<>());
		assertThat(config.crossDomain().getMapping().length,equalTo(0));
	}

	@Test
	public void testEbayCrossDomainOn() {
		ConfigureBeans config = new ConfigureBeans(false,"/*",new HashMap<>());
		assertThat(config.ebayCrossDomain().getMapping().length,equalTo(1));
	}
	@Test
	public void testEbayCrossDomainOff() {
		ConfigureBeans config = new ConfigureBeans(true,"/*",new HashMap<>());
		assertThat(config.ebayCrossDomain().getMapping().length,equalTo(0));
	}

}
