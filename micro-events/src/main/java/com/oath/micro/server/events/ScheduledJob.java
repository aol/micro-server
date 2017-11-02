package com.oath.micro.server.events;



public interface ScheduledJob<T> {
	SystemData scheduleAndLog();
}
