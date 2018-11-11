package com.oath.micro.server.reactive;
import java.util.concurrent.ScheduledExecutorService;

import com.oath.cyclops.types.stream.Connectable;
import cyclops.reactive.ReactiveSeq;
import lombok.AllArgsConstructor;


import com.oath.micro.server.events.ScheduledJob;
import com.oath.micro.server.events.SystemData;

@AllArgsConstructor
public class JobSchedular {
	
	private final ScheduledExecutorService ex;
	
	public <T,K,V> Connectable<SystemData<K,V>> schedule(String expression, ScheduledJob<T> job){
		return ReactiveSeq.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.schedule(expression,ex); 
	}
	public <T,K,V> Connectable<SystemData<K,V>> scheduleFixedDelay(long fixedDelay, ScheduledJob<T> job){
		return ReactiveSeq.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.scheduleFixedDelay(fixedDelay,ex); 
	}
	
	public <T,K,V> Connectable<SystemData<K,V>> scheduleFixedRate(long fixedRate, ScheduledJob<T> job){
		return ReactiveSeq.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.scheduleFixedRate(fixedRate,ex); 
	}
	
}
