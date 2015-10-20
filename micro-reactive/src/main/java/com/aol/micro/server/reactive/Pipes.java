package com.aol.micro.server.reactive;

import java.util.Optional;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.simple.react.async.Adapter;
import com.aol.simple.react.async.pipes.LazyReactors;
import com.aol.simple.react.async.subscription.Subscription;
import com.aol.simple.react.stream.traits.LazyFutureStream;

/**
 * Store for Pipes for cross-thread communication
 * 
 * @author johnmcclean
 *
 */
public class Pipes extends com.aol.simple.react.async.pipes.Pipes{
	

	/**
	 * @param key : Adapter identifier
	 * @return selected Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K,V> Optional<Adapter<V>> get(K key){
		return com.aol.simple.react.async.pipes.Pipes.get(key);
	}
	/**
	 * Register a Queue, and get back a listening SequenceM
	 * 
	 * 
	 * 
	 *  <pre>
	 * {@code
	 * SequenceM<String> stream = Pipes.registerForSequential("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		stream.filter(it->it!=null)
		      .peek(this::process)
		      .sync()
		      .forEach(System.out::println);
	 * 
	 * }</pre>
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue, optimisied for CPU bound operation
	 */
	public static <V> SequenceM<V> registerForSequential(Object key, Adapter<V> adapter){
		com.aol.simple.react.async.pipes.Pipes.register(key, adapter);
		return adapter.stream();
	}
	
	/**
	 * Register a Queue, and get back a listening LazyFutureStream optimized for CPU Bound operations
	 * 
	 * 
	 *  <pre>
	 * {@code
	 * LazyFutureStream<String> stream = Pipes.registerForCPU("test", QueueFactories.
											<String>boundedNonBlockingQueue(100)
												.build());
		stream.filter(it->it!=null)
		      .peek(this::process)
		      .forEach(System.out::println);
	 * 
	 * }</pre>
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue, optimisied for CPU bound operation
	 */
	public static <V> LazyFutureStream<V> registerForCPU(Object key, Adapter<V> adapter){
		com.aol.simple.react.async.pipes.Pipes.register(key, adapter);
		Subscription sub = new Subscription();
		return LazyReactors.cpuReact.from(adapter.stream(sub)).withSubscription(sub);
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
		      .peek(this::load)
		      .run(System.out::println);
	 * 
	 * }</pre>
	 * 
	 * @param key : Adapter identifier
	 * @param adapter
	 * @return LazyFutureStream from supplied Queue
	 */
	public static <V> LazyFutureStream<V> registerForIO(Object key, Adapter<V> adapter){
		com.aol.simple.react.async.pipes.Pipes.register(key, adapter);
		Subscription sub = new Subscription();
		return LazyReactors.ioReact.from(adapter.stream(sub)).withSubscription(sub);
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> SequenceM<V> stream(Object key){
		return Pipes.<Object,V>get(key).get().stream();
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> LazyFutureStream<V> futureStreamIOBound(Object key){
		Subscription sub = new Subscription();
		return LazyReactors.ioReact.from(Pipes.<Object,V>get(key).get().stream(sub)).withSubscription(sub);
	}
	/**
	 * @param key : Queue identifier
	 * @return LazyFutureStream that reads from specified Queue
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <V> LazyFutureStream<V> futureStreamCPUBound(Object key){
		Subscription sub = new Subscription();
		return LazyReactors.cpuReact.from(Pipes.<Object,V>get(key).get().stream(sub)).withSubscription(sub);
	}

	

}
