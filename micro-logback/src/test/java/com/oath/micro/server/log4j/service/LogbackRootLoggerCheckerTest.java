package com.oath.micro.server.log4j.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import com.oath.micro.server.logback.service.LogbackRootLoggerChecker;



public class LogbackRootLoggerCheckerTest {
	
	LogbackRootLoggerChecker checker;
	
	@Before
	public void setUp() {
		checker = new LogbackRootLoggerChecker(true, "INFO");
	}
	
	@Test
	public void testCheck() {
		checker.check();
		 Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		 Level actualLevel = root.getLevel();
		assertThat(actualLevel, is(Level.INFO));
		
		checker.setCorrectLevelStr("DEBUG");
		checker.check();
		actualLevel = root.getLevel();
		assertThat(actualLevel, is(Level.DEBUG));
		
		checker.setActive(false);
		checker.setCorrectLevelStr("WARN");
		checker.check();
		actualLevel = root.getLevel();
		assertThat(actualLevel, is(Level.DEBUG));
	}

}
