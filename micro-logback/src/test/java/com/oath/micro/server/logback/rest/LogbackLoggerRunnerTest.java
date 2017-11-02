package com.oath.micro.server.logback.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.testing.RestAgent;

@Microserver
public class LogbackLoggerRunnerTest {

	RestAgent rest = new RestAgent();
	MicroserverApp server;

	@Before
	public void startServer() {
		server = new MicroserverApp(() -> "logback");
		server.start();
	}

	@After
	public void stopServer() {
		server.stop();
	}

	@Test
	public void testChangeToWarn() {
		assertThat(rest.get("http://localhost:8080/logback/logback/logger/change/to/warn/common"), is("WARN"));
	}
	

}
