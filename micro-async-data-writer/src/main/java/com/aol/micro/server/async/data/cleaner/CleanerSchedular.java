package com.aol.micro.server.async.data.cleaner;

import java.util.concurrent.ScheduledExecutorService;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.google.common.eventbus.EventBus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CleanerSchedular {

	private ListX<DataCleaner> cleaner;
	private final ScheduledExecutorService executor;
	private final EventBus bus;
	
	public void schedule(){
		cleaner.forEach(cl->{
			ReactiveSeq.generate(()->cl.scheduleAndLog())
						.peek(sd->bus.post(sd))
					   .schedule(cl.getCron(), executor);
		});
	}
}
