package com.aol.micro.server.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import org.pcollections.ConsPStack;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import org.pcollections.PStack;

import com.aol.micro.server.rest.jackson.JacksonUtil;

public class ActiveEvents<T extends BaseEventInfo> {

	private volatile PMap<String, T> active = HashTreePMap.empty();
	private volatile PStack<Map> recentlyFinished=  ConsPStack.empty();
	private volatile int events = 0;
	private volatile int added = 0;
	private volatile int removed = 0;

	public void active(String key, T data) {
		active = active.plus(key, data);
		events++;
		added++;
	}
	public  void finished(String key) {
		finished(key,ImmutableMap.of());
	}
	public void finished(String key, ImmutableMap data) {
		recentlyFinished =recentlyFinished.plus(wrapInMap(active.get(key),data));
		active=active.minus(key);
		removed++;
		if(recentlyFinished.size()>10)
			recentlyFinished.minus(recentlyFinished.size()-1);
			
		
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
