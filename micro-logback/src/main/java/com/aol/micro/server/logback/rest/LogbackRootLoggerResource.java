package com.aol.micro.server.logback.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import com.aol.micro.server.auto.discovery.SingletonRestResource;
import com.aol.micro.server.logback.service.LogbackRootLoggerChecker;

@Component
@Path("/logback/rootlogger")
public class LogbackRootLoggerResource implements SingletonRestResource {

	private final Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	private final LogbackRootLoggerChecker checker;
	@Autowired
	public LogbackRootLoggerResource(final LogbackRootLoggerChecker checker) {
		this.checker = checker;
	}
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get/level")
	public String getLevel() {
		return root.getLevel().toString();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/all")
	public String changeToAll() {
		root.setLevel(Level.ALL);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/debug")
	public String changeToDebug() {
		root.setLevel(Level.DEBUG);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/error")
	public String changeToError() {
		root.setLevel(Level.ERROR);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/info")
	public String changeToInfo() {
		root.setLevel(Level.INFO);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/off")
	public String changeToOff() {
		root.setLevel(Level.OFF);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/trace")
	public String changeToTrace() {
		root.setLevel(Level.TRACE);
		return getLevel();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/warn")
	public String changeToWarn() {
		root.setLevel(Level.WARN);
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
