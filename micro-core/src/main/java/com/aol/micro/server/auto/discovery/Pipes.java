package com.aol.micro.server.auto.discovery;

import java.util.Map;

import com.aol.simple.react.async.Queue;
import com.aol.simple.react.async.Topic;
import com.aol.simple.react.stream.traits.LazyFutureStream;
import com.google.common.collect.Maps;

public class Pipes {
	
	private static Map<Object,Queue> registered = Maps.newConcurrentMap();
	private static Map<Object,Topic> registeredTopics = Maps.newConcurrentMap();
	
	public static <K,V> Queue<V> get(K key){
		return registered.get(key);
	}
	public static <K,V> Topic<V> getTopic(K key){
		return registeredTopics.get(key);
	}
	
	public static <V> LazyFutureStream<V> register(Object key, Queue<V> queue){
		registered.put(key, queue);
		return LazyFutureStream.of(queue.stream());
	}
	public static <V> LazyFutureStream<V> stream(Object key){
		return LazyFutureStream.of(registered.get(key).stream());
	}
	public static <V> LazyFutureStream<V> registerTopic(Object key, Topic<V> topic){
		registeredTopics.put(key, topic);
		return LazyFutureStream.of(topic.stream());
	}
	public static <V> LazyFutureStream<V> streamTopic(Object key){
		return LazyFutureStream.of(registeredTopics.get(key).stream());
	}
}
