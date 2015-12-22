package com.aol.micro.server.logback.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import com.aol.micro.server.auto.discovery.SingletonRestResource;

@Component
@Path("/logback/logger")
public class LogbackLoggerResource implements SingletonRestResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get/level/{loggerName}")
	public String getLevel(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);	
		return logger.getLevel().toString();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/all/{loggerName}")
	public String changeToAll(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.ALL);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/debug/{loggerName}")
	public String changeToDebug(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.DEBUG);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/error/{loggerName}")
	public String changeToError(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.ERROR);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/info/{loggerName}")
	public String changeToInfo(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.INFO);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/off/{loggerName}")
	public String changeToOff(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.OFF);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/trace/{loggerName}")
	public String changeToTrace(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.TRACE);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/warn/{loggerName}")
	public String changeToWarn(@PathParam("loggerName") final String loggerName) {
		Logger logger = (Logger) LoggerFactory.getLogger(loggerName);
		changeLevel(logger, Level.WARN);
		return getLevel(loggerName);
	}

	private void changeLevel(Logger logger, Level newLevel) {
		logger.warn("Changing logging level from " + logger.getLevel() + " to " + newLevel);
		logger.setLevel(newLevel);
	}
	
}
