package com.aol.micro.server.reactive;

import java.util.Optional;
import java.util.function.Function;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.cyclops.trycatch.Failure;
import com.aol.cyclops.trycatch.Success;
import com.aol.cyclops.trycatch.Try;
import com.aol.simple.react.async.Adapter;
import com.aol.simple.react.async.pipes.LazyReactors;
import com.aol.simple.react.stream.lazy.LazyReact;
import com.aol.simple.react.stream.traits.LazyFutureStream;
import com.aol.simple.react.threads.ParallelElasticPools;
import com.aol.simple.react.threads.SequentialElasticPools;

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
	default <K,T> SequenceM<T> sequentialStream(K key){
		return Pipes.stream(key);
	}
	default <K,T> SequenceM<T> cpuFutureStream(K key){
		return Pipes.futureStreamCPUBound(key);
	}
	default  LazyReact ioStreamBuilder(){
		return LazyReactors.ioReact;
	}
	
	default LazyReact cpuStreamBuilder(){
		return LazyReactors.cpuReact;
	}
	
	default <T> SequenceM<T> switchToSequential(LazyFutureStream<T> stream){
		return SequenceM.fromStream(stream);
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
