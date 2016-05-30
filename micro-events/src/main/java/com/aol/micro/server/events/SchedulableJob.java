package com.aol.micro.server.events;

import com.aol.micro.server.events.ScheduledJob;

@SuppressWarnings("rawtypes")
public interface SchedulableJob extends ScheduledJob {  
	boolean isScheduled();
}
