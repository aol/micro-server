package com.aol.micro.server.log4j.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.log4j.service.Log4jRootLoggerChecker;

@Component
@Path("/rootlogger")
public class Log4jRootLoggerResource implements SingletonRestResource {
	
	@Autowired
	Log4jRootLoggerChecker checker;

	public Log4jRootLoggerResource() {
		BasicConfigurator.configure();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get/level")
	public String getLevel() {
		return Logger.getRootLogger().getLevel().toString();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/all")
	public String changeToAll() {
		Logger.getRootLogger().setLevel(Level.ALL);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/debug")
	public String changeToDebug() {
		Logger.getRootLogger().setLevel(Level.DEBUG);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/error")
	public String changeToError() {
		Logger.getRootLogger().setLevel(Level.ERROR);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/fatal")
	public String changeToFatal() {
		Logger.getRootLogger().setLevel(Level.FATAL);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/info")
	public String changeToInfo() {
		Logger.getRootLogger().setLevel(Level.INFO);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/off")
	public String changeToOff() {
		Logger.getRootLogger().setLevel(Level.OFF);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/trace")
	public String changeToTrace() {
		Logger.getRootLogger().setLevel(Level.TRACE);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/warn")
	public String changeToWarn() {
		Logger.getRootLogger().setLevel(Level.WARN);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/checker/is/{active}")
	public String setCheckerActive(@PathParam("active") final boolean active) {
		checker.setActive(active);
		return String.valueOf(checker.isActive());
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/checker/level/{correctLevelStr}")
	public String setCheckerCorrectLevel(@PathParam("correctLevelStr") final String correctLevelStr) {
		checker.setCorrectLevelStr(correctLevelStr);
		return checker.getCorrectLevelStr();
	}
}
