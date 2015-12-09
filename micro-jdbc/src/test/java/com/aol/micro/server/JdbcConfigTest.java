package com.aol.micro.server;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.datasource.JdbcConfig;

public class JdbcConfigTest {

	JdbcConfig config;

	@Before
	public void setUp() throws Exception {
		Config.instance();
		config = new JdbcConfig("driverClassName", "url", "username", "password", "showSql", "mysql", "none", new Properties(), null, "false");

	}

	@Test
	public void test() {
		assertThat(config, notNullValue());
		assertThat(config.getDdlAuto(), is("none"));
		assertThat(config.getDialect(), is("mysql"));
		assertThat(config.getDriverClassName(), is("driverClassName"));
		assertThat(config.getUrl(), is("url"));
		assertThat(config.getUsername(), is("username"));
		assertThat(config.getPassword(), is("password"));
		assertThat(config.getShowSql(), is("showSql"));		
	}
}
