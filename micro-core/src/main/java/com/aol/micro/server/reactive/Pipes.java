package com.aol.micro.server.reactive;

import java.util.Map;
import java.util.Optional;

import com.aol.simple.react.async.Adapter;
import com.aol.simple.react.async.Queue;
import com.aol.simple.react.async.Topic;
import com.aol.simple.react.stream.traits.LazyFutureStream;
import com.google.common.collect.Maps;

/**
 * Store for Pipes for cross-thread communication
 * 
 * @author johnmcclean
 *
 */
public class Pipes {
	
	private static Map<Object,Adapter<?>> registered = Maps.newConcurrentMap();
	
	
	/**
	 * @param key : Adapter identifier
	 * @return selected Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K,V> Optional<Adapter<V>> get(K key){
		return Optional.ofNullable((Adapter)registered.get(key));
	}
	
	/**
	 * Register a Queue
	 * 
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue
	 */
	public static <V> LazyFutureStream<V> register(Object key, Adapter<V> adapter){
		registered.put(key, adapter);
		return LazyFutureStream.of(adapter.stream());
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> LazyFutureStream<V> stream(Object key){
		return LazyFutureStream.of(((Adapter)registered.get(key)).stream());
	}
	
}
