package com.aol.micro.server.async.data.loader;

import java.util.concurrent.ScheduledExecutorService;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.google.common.eventbus.EventBus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoaderSchedular {

	private ListX<DataLoader> loader;
	private final ScheduledExecutorService executor;
	private final EventBus bus;

	public void schedule() {
		loader.forEach(dl -> {
			ReactiveSeq	.generate(() -> dl.scheduleAndLog())
						.peek(sd -> bus.post(sd))
						.schedule(dl.getCron(), executor);
		});
	}
}
