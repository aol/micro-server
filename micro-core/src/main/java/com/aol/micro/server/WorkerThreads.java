package com.aol.micro.server;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import com.aol.cyclops.control.FluentFunctions;

public class WorkerThreads {

	public final static Supplier<Executor> ioExecutor = FluentFunctions.of(WorkerThreads::ioWorkers)
														  .memoize();
	private static Executor ioWorkers(){
		return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
	}
}
