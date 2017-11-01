package com.oath.micro.server.logback.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LogbackRootLoggerResourceTest {

	LogbackRootLoggerResource logbackResource;

	@Before
	public void setUp() {
		logbackResource = new LogbackRootLoggerResource(null);
	}

	@Test
	public void testChangeToAll() {
		assertThat(logbackResource.changeToAll(), is("ALL"));
	}

	@Test
	public void testChangeToDebug() {
		assertThat(logbackResource.changeToDebug(), is("DEBUG"));
	}

	@Test
	public void testChangeToError() {
		assertThat(logbackResource.changeToError(), is("ERROR"));
	}

	@Test
	public void testChangeToInfo() {
		assertThat(logbackResource.changeToInfo(), is("INFO"));
	}

	@Test
	public void testChangeToOff() {
		assertThat(logbackResource.changeToOff(), is("OFF"));
	}

	@Test
	public void testChangeToTrace() {
		assertThat(logbackResource.changeToTrace(), is("TRACE"));
	}

	@Test
	public void testChangeToWarn() {
		assertThat(logbackResource.changeToWarn(), is("WARN"));
	}

}
