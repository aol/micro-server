package com.aol.micro.server.reactive;

import java.util.Optional;

import com.aol.cyclops.control.LazyReact;
import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.control.Try;
import com.aol.cyclops.control.Try.Failure;
import com.aol.cyclops.control.Try.Success;
import com.aol.cyclops.data.async.Adapter;
import com.aol.cyclops.react.async.pipes.LazyReactors;
import com.aol.cyclops.types.futurestream.LazyFutureStream;

/**
 * Mixin / Trait for Reactive behaviour via simple-react
 * 
 * @author johnmcclean
 *
 */
public interface Reactive {
	/**
	 * Add a value to an simple-react Async.Queue
	 * 
	 * @see Pipes#register(Object, com.aol.simple.react.async.Adapter)
	 * 
	 * @param key : identifier for registered Queue
	 * @param value : value to add to Queue
	 */
	default<K,V> Try<Boolean,MissingPipeException> enqueue(K key,V value){
		Optional<Adapter<V>> queue = Pipes.get(key);
		queue.map(adapter -> adapter.offer(value));
	
		return queue.isPresent() ? Success.of(true) : 
						Failure.of(new MissingPipeException("Missing queue for key : " + key.toString()));
		
		
	}
	
	default <K,T> LazyFutureStream<T> ioFutureStream(K key){
		return Pipes.futureStreamIOBound(key);
	}
	default <K,T> ReactiveSeq<T> sequentialStream(K key){
		return Pipes.stream(key);
	}
	default <K,T> LazyFutureStream<T> cpuFutureStream(K key){
		return Pipes.futureStreamCPUBound(key);
	}
	default  LazyReact ioStreamBuilder(){
		return LazyReactors.ioReact;
	}
	
	default LazyReact cpuStreamBuilder(){
		return LazyReactors.cpuReact;
	}
	
	default <T> ReactiveSeq<T> switchToSequential(LazyFutureStream<T> stream){
		return stream.stream();
	}
	
	/**
	 * Switch LazyFutureStream into execution mode suitable for IO (reuse ioReactors task executor)
	 * 
	 * @param stream to convert to IO mode
	 * @return LazyFutureStream in IO mode
	 */
	default <T> LazyFutureStream<T> switchToIO(LazyFutureStream<T> stream){
		LazyReact react = LazyReactors.ioReact;
		return stream.withTaskExecutor(react.getExecutor()).withRetrier(react.getRetrier());
	}
	/**
	 *  Switch LazyFutureStream into execution mode suitable for CPU bound execution (reuse cpuReactors task executor)
	 * 
	 * @param stream to convert to CPU bound mode
	 * @return LazyFutureStream in CPU bound mode
	 */
	default <T> LazyFutureStream<T> switchToCPU(LazyFutureStream<T> stream){
		LazyReact react = LazyReactors.cpuReact;
		return stream.withTaskExecutor(react.getExecutor()).withRetrier(react.getRetrier());
	}
	
	
}
