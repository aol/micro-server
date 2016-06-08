package com.aol.micro.server.events;

public interface SchedulableJob extends ScheduledJob {  
	boolean isScheduled();
}
