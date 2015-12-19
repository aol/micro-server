package com.aol.micro.server.logback.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;



public class LogbackLoggerResourceTest {
	
	String loggerName = "common";
	
	LogbackLoggerResource logbackResource;
	
	@Before
	public void setUp() {
		logbackResource = new LogbackLoggerResource();
	}
	
	@Test
	public void testChangeToAll() {
		assertThat(logbackResource.changeToAll(loggerName), is("ALL"));
	}
	
	@Test
	public void testChangeToDebug() {
		assertThat(logbackResource.changeToDebug(loggerName), is("DEBUG"));
	}
	
	@Test
	public void testChangeToError() {
		assertThat(logbackResource.changeToError(loggerName), is("ERROR"));
	}
	
	@Test
	public void testChangeToInfo() {
		assertThat(logbackResource.changeToInfo(loggerName), is("INFO"));
	}
	
	@Test
	public void testChangeToOff() {
		assertThat(logbackResource.changeToOff(loggerName), is("OFF"));
	}
	
	@Test
	public void testChangeToTrace() {
		assertThat(logbackResource.changeToTrace(loggerName), is("TRACE"));
	}
	
	@Test
	public void testChangeToWarn() {
		assertThat(logbackResource.changeToWarn(loggerName), is("WARN"));
	}

}
