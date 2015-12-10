package com.aol.micro.server.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

public class ActiveEvents<T extends BaseEventInfo> {

	private final Map<String, T> active = new ConcurrentHashMap<>();
	private final Deque<Map> recentlyFinished=  new ConcurrentLinkedDeque<>();
	private final AtomicInteger events = new AtomicInteger(0);
	private final AtomicInteger added = new AtomicInteger(0);
	private final AtomicInteger removed = new AtomicInteger(0);

	public void active(String key, T data) {
		active.put(key, data);
		events.incrementAndGet();
		added.incrementAndGet();
	}
	public  void finished(String key) {
		finished(key,ImmutableMap.of());
	}
	public void finished(String key, ImmutableMap data) {
		recentlyFinished.push(wrapInMap(active.get(key), data));
		active.remove(key);
		removed.incrementAndGet();
		
		if(recentlyFinished.size()>10)
			synchronized (this) {
				if(recentlyFinished.size()>10)
					recentlyFinished.pollFirst();
			}
	}
	
	private Map wrapInMap(T event, ImmutableMap data){
		Long time = System.currentTimeMillis();
		DateFormat format = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z");
		String formatted = format.format(time);
		Long change = Runtime.getRuntime().freeMemory() - event.getFreeMemory();
		return ImmutableMap.builder().putAll(data)
				.putAll(ImmutableMap.of("event",event,"completed",time,"completed-formated",formatted,"time-taken",time-event.getStartedAt()
						,"memory-change",change )).build();
	}
	/*
	 * We don't want to expose the active map externally as access would not be thread safe
	 * 	 
	 * */
	public  String toString(){
		Map result = toMap();
		return JacksonUtil.serializeToJson(result);
	}

	private Map toMap() {
		Map result = Maps.newHashMap();
		result.put("events", events.get());
		result.put("active", active);
		result.put("added",added.get());
		result.put("removed",removed.get());
		result.put("recently-finished", recentlyFinished);
		return result;
	}
	
	public int events(){
		return events.get();
	}

	public int size() {
		return active.size();
	}
}
