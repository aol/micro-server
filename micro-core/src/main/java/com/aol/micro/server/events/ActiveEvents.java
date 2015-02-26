package com.aol.micro.server.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Queue;

import com.aol.micro.server.rest.JacksonUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ActiveEvents<T extends BaseEventInfo> {

	private final Map<String, T> active = Maps.newHashMap();
	private final Queue<Map> recentlyFinished=  Lists.newLinkedList();
	private volatile int events = 0;
	private volatile int added = 0;
	private volatile int removed = 0;

	public synchronized void active(String key, T data) {
		active.put(key, data);
		events++;
		added++;
	}
	public synchronized void finished(String key) {
		finished(key,ImmutableMap.of());
	}
	public synchronized void finished(String key, ImmutableMap data) {
		recentlyFinished.add(wrapInMap(active.get(key),data));
		active.remove(key);
		removed++;
		if(recentlyFinished.size()>10)
			recentlyFinished.remove();
			
		
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
	public synchronized String toString(){
		Map result = toMap();
		return JacksonUtil.serializeToJson(result);
	}

	private Map toMap() {
		Map result = Maps.newHashMap();
		result.put("events", events);
		result.put("active", active);
		result.put("added",added);
		result.put("removed",removed);
		result.put("recently-finished",recentlyFinished);
		return result;
	}
	
	public int events(){
		return events;
	}

	public int size() {
		return active.size();
	}
}
