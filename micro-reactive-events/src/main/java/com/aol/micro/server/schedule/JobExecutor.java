package com.aol.micro.server.schedule;
import java.util.concurrent.ScheduledExecutorService;

import lombok.AllArgsConstructor;

import com.aol.cyclops.sequence.HotStream;
import com.aol.cyclops.sequence.SequenceM;
import com.aol.micro.server.events.ScheduledJob;
import com.aol.micro.server.events.SystemData;

@AllArgsConstructor
public class JobExecutor {
	
	private final ScheduledExecutorService ex;
	
	public <T,K,V> HotStream<SystemData<K,V>> schedule(String expression, ScheduledJob<T> job){
		return SequenceM.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.schedule(expression,ex); 
	}
	public <T,K,V> HotStream<SystemData<K,V>> scheduleFixedDelay(long fixedDelay, ScheduledJob<T> job){
		return SequenceM.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.scheduleFixedDelay(fixedDelay,ex); 
	}
	
	public <T,K,V> HotStream<SystemData<K,V>> scheduleFixedRate(long fixedRate, ScheduledJob<T> job){
		return SequenceM.generate(()->"new job")
						.<SystemData<K,V>>map(drop->job.scheduleAndLog())
						.scheduleFixedRate(fixedRate,ex); 
	}
	
}
