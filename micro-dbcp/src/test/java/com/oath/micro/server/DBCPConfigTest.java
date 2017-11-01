package com.oath.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.spring.datasource.DBCPConfig;

public class DBCPConfigTest {

	DBCPConfig config;

	@Before
	public void setUp() throws Exception {
		config = new DBCPConfig(true, "SELECT 1", -1, 1800000, 1800000, 3, true, true);

	}

	@Test
	public void test() {
		assertThat(config, notNullValue());
		assertThat(config.isTestOnBorrow(), is(true));
		assertThat(config.getValidationQuery(), is("SELECT 1"));
		assertThat(config.getMaxTotal(), is(-1));
		assertThat(config.getMinEvictableIdleTime(), is(1800000L));
		assertThat(config.getTimeBetweenEvictionRuns(), is(1800000L));
		assertThat(config.getNumTestsPerEvictionRun(), is(3));
		assertThat(config.isTestWhileIdle(), is(true));
		assertThat(config.isTestOnReturn(), is(true));

	}

}
