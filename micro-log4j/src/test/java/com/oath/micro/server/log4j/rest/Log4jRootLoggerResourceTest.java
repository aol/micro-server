package com.oath.micro.server.log4j.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;


public class Log4jRootLoggerResourceTest {

	Log4jRootLoggerResource log4jResource;

	@Before
	public void setUp() {
		BasicConfigurator.configure();
		log4jResource = new Log4jRootLoggerResource();
	}

	@Test
	public void testChangeToAll() {
		assertThat(log4jResource.changeToAll(), is("ALL"));
	}

	@Test
	public void testChangeToDebug() {
		assertThat(log4jResource.changeToDebug(), is("DEBUG"));
	}

	@Test
	public void testChangeToError() {
		assertThat(log4jResource.changeToError(), is("ERROR"));
	}

	@Test
	public void testChangeToFatal() {
		assertThat(log4jResource.changeToFatal(), is("FATAL"));
	}

	@Test
	public void testChangeToInfo() {
		assertThat(log4jResource.changeToInfo(), is("INFO"));
	}

	@Test
	public void testChangeToOff() {
		assertThat(log4jResource.changeToOff(), is("OFF"));
	}

	@Test
	public void testChangeToTrace() {
		assertThat(log4jResource.changeToTrace(), is("TRACE"));
	}

	@Test
	public void testChangeToWarn() {
		assertThat(log4jResource.changeToWarn(), is("WARN"));
	}

}
