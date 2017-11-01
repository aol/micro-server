package com.oath.micro.server.log4j.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.oath.micro.server.auto.discovery.SingletonRestResource;

@Component
@Path("/log4j/logger")
public class Log4jLoggerResource implements SingletonRestResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get/level/{loggerName}")
	public String getLevel(@PathParam("loggerName") final String loggerName) {
		return Logger.getLogger(loggerName).getLevel().toString();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/all/{loggerName}")
	public String changeToAll(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.ALL);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/debug/{loggerName}")
	public String changeToDebug(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.DEBUG);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/error/{loggerName}")
	public String changeToError(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.ERROR);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/fatal/{loggerName}")
	public String changeToFatal(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.FATAL);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/info/{loggerName}")
	public String changeToInfo(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.INFO);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/off/{loggerName}")
	public String changeToOff(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.OFF);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/trace/{loggerName}")
	public String changeToTrace(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.TRACE);
		return getLevel(loggerName);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/change/to/warn/{loggerName}")
	public String changeToWarn(@PathParam("loggerName") final String loggerName) {
		Logger.getLogger(loggerName).setLevel(Level.WARN);
		return getLevel(loggerName);
	}

}
