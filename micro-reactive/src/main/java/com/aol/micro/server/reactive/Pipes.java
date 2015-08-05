package com.aol.micro.server.reactive;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.aol.simple.react.async.Adapter;
import com.aol.simple.react.async.QueueFactories;
import com.aol.simple.react.stream.traits.LazyFutureStream;

/**
 * Store for Pipes for cross-thread communication
 * 
 * @author johnmcclean
 *
 */
public class Pipes {
	
	private static final ConcurrentMap<Object,Adapter<?>> registered = new ConcurrentHashMap<>();
	
	
	/**
	 * @param key : Adapter identifier
	 * @return selected Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K,V> Optional<Adapter<V>> get(K key){
		return Optional.ofNullable((Adapter)registered.get(key));
	}
	/**
	 * Register a Queue, and get back a listening LazyFutureStream that runs on a single thread
	 * (not the calling thread)
	 * 
	 * <pre>
	 * {@code
	 * LazyFutureStream<String> stream = Pipes.register("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		stream.filter(it->it!=null).peek(System.out::println).run();
	 * 
	 * }</pre>
	 * 
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue, optimisied for CPU bound operation
	 */
	public static <V> LazyFutureStream<V> register(Object key, Adapter<V> adapter){
		registered.put(key, adapter);
		return  LazyFutureStream.of(adapter.stream());
	}
	
	/**
	 * Register a Queue, and get back a listening LazyFutureStream optimized for CPU Bound operations
	 * 
	 * Convert the LazyFutureStream to async mode to fan out operations across threads, after the first fan out operation definition 
	 * it should be converted to sync mode
	 * 
	 *  <pre>
	 * {@code
	 * LazyFutureStream<String> stream = Pipes.registerForCPU("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		stream.filter(it->it!=null)
		      .async()
		      .peek(this::process)
		      .sync()
		      .forEach(System.out::println);
	 * 
	 * }</pre>
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue, optimisied for CPU bound operation
	 */
	public static <V> LazyFutureStream<V> registerForCPU(Object key, Adapter<V> adapter){
		registered.put(key, adapter);
		return Reactors.cpuReact.of(adapter.stream());
	}
	/**
	 * Register a Queue, and get back a listening LazyFutureStream optimized for IO Bound operations
	 * 
	 * <pre>
	 * {@code
	 * LazyFutureStream<String> stream = Pipes.registerForIO("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		stream.filter(it->it!=null)
		      .async()
		      .peek(this::load)
		      .sync()
		      .run(System.out::println);
	 * 
	 * }</pre>
	 * 
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue
	 */
	public static <V> LazyFutureStream<V> registerForIO(Object key, Adapter<V> adapter){
		registered.put(key, adapter);
		return Reactors.ioReact.of(adapter.stream());
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> LazyFutureStream<V> stream(Object key){
		return LazyFutureStream.of(((Adapter)registered.get(key)).stream());
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> LazyFutureStream<V> streamIOBound(Object key){
		return Reactors.ioReact.of(((Adapter)registered.get(key)).stream());
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> LazyFutureStream<V> streamCPUBound(Object key){
		return Reactors.cpuReact.of(((Adapter)registered.get(key)).stream());
	}

	public static void clear() {
		 registered.clear();
		
	}
	
}
