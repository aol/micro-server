package com.aol.micro.server.events;



public interface ScheduledJob<T> {
	SystemData scheduleAndLog();
}
