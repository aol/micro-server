package com.aol.micro.server.log4j.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;



public class Log4jLoggerResourceTest {
	
	String loggerName = "common";
	
	Log4jLoggerResource log4jResource;
	
	@Before
	public void setUp() {
		log4jResource = new Log4jLoggerResource();
	}
	
	@Test
	public void testChangeToAll() {
		assertThat(log4jResource.changeToAll(loggerName), is("ALL"));
	}
	
	@Test
	public void testChangeToDebug() {
		assertThat(log4jResource.changeToDebug(loggerName), is("DEBUG"));
	}
	
	@Test
	public void testChangeToError() {
		assertThat(log4jResource.changeToError(loggerName), is("ERROR"));
	}
	
	@Test
	public void testChangeToFatal() {
		assertThat(log4jResource.changeToFatal(loggerName), is("FATAL"));
	}
	
	@Test
	public void testChangeToInfo() {
		assertThat(log4jResource.changeToInfo(loggerName), is("INFO"));
	}
	
	@Test
	public void testChangeToOff() {
		assertThat(log4jResource.changeToOff(loggerName), is("OFF"));
	}
	
	@Test
	public void testChangeToTrace() {
		assertThat(log4jResource.changeToTrace(loggerName), is("TRACE"));
	}
	
	@Test
	public void testChangeToWarn() {
		assertThat(log4jResource.changeToWarn(loggerName), is("WARN"));
	}

}
