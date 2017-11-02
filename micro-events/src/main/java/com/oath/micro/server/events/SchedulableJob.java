package com.oath.micro.server.events;

public interface SchedulableJob extends ScheduledJob {  
	boolean isScheduled();
}
