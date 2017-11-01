package com.aol.micro.server.reactive;
import java.util.concurrent.ScheduledExecutorService;

import com.oath.cyclops.types.stream.HotStream;
import cyclops.reactive.ReactiveSeq;
import lombok.AllArgsConstructor;


import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;

@AllArgsConstructor
public class JobSchedular {
	
	private final ScheduledExecutorService ex;
	
	public <T,K,V> HotStream<SystemData<K,V>> schedule(String expression, ScheduledJob<T> job){
		return ReactiveSeq.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.schedule(expression,ex); 
	}
	public <T,K,V> HotStream<SystemData<K,V>> scheduleFixedDelay(long fixedDelay, ScheduledJob<T> job){
		return ReactiveSeq.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.scheduleFixedDelay(fixedDelay,ex); 
	}
	
	public <T,K,V> HotStream<SystemData<K,V>> scheduleFixedRate(long fixedRate, ScheduledJob<T> job){
		return ReactiveSeq.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.scheduleFixedRate(fixedRate,ex); 
	}
	
}