package com.oath.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.spring.datasource.HikariCPConfig;

public class HikariCPConfigTest {
	
	HikariCPConfig config;

	@Before
	public void setUp() throws Exception {
		config = new HikariCPConfig(30, 2, 6000);

	}

	@Test
	public void test() {
		assertThat(config, notNullValue());
		assertThat(config.getMaxPoolSize(), is(30));
		assertThat(config.getMinimumIdle(), is(2));		
		assertThat(config.getIdleTimeout(), is(6000L));
	}

}
