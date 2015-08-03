package com.aol.micro.server.reactive;

import java.util.Optional;
import java.util.function.Function;

import com.aol.cyclops.trycatch.Failure;
import com.aol.cyclops.trycatch.Success;
import com.aol.cyclops.trycatch.Try;
import com.aol.simple.react.async.Adapter;
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
	
	
	/**
	 * 
	 * Generate a sequentially executing single-threaded a LazyFutureStream that executes all tasks directly without involving
	 * a task executor between each stage (unless async operator invoked). A preconfigured LazyReact builder that will be supplied as
	 * input to the function supplied. The user Function should create a LazyFutureStream with any
	 * business logic stages predefined. This method will handle elastic scaling and pooling of Executor
	 * services. User code should call a terminal op on the returned LazyFutureStream
	 * @see Reactive#run(com.aol.simple.react.stream.traits.LazyFutureStream)
	 * 
	 * @param react Function that generates a LazyFutureStream from a LazyReact builder
	 * @return Generated LazyFutureStream
	 */
	default <T> LazyFutureStream<T> sync(Function<LazyReact,LazyFutureStream<T>> react){
		 LazyReact r  =SequentialElasticPools.lazyReact.nextReactor().withAsync(false);
		 return react.apply( r)
				 	.onFail(e->{ SequentialElasticPools.lazyReact.populate(r); throw e;})
				 	.peek(i->SequentialElasticPools.lazyReact.populate(r));
		 				 	
	}
	
	/**
	 * Switch LazyFutureStream into execution mode suitable for IO (reuse ioReactors task executor)
	 * 
	 * @param stream to convert to IO mode
	 * @return LazyFutureStream in IO mode
	 */
	default <T> LazyFutureStream<T> swithToIO(LazyFutureStream<T> stream){
		LazyReact react = Reactors.ioReact;
		return stream.withTaskExecutor(react.getExecutor()).withRetrier(react.getRetrier());
	}
	/**
	 *  Switch LazyFutureStream into execution mode suitable for CPU bound execution (reuse cpuReactors task executor)
	 * 
	 * @param stream to convert to CPU bound mode
	 * @return LazyFutureStream in CPU bound mode
	 */
	default <T> LazyFutureStream<T> swithToCPU(LazyFutureStream<T> stream){
		LazyReact react = Reactors.cpuReact;
		return stream.withTaskExecutor(react.getExecutor()).withRetrier(react.getRetrier());
	}
	/**
	 * @return Stream builder for IO Bound Streams
	 */
	default MicroLazyReact ioStream(){
		return new MicroLazyReact(Reactors.ioReact);
	}
	/**
	 * @return  Stream builder for CPU Bound Streams
	 */
	default MicroLazyReact cpuStream(){
		return new MicroLazyReact(Reactors.cpuReact);
	}
	/**
	 * Generate a multi-threaded LazyFutureStream that executes all tasks via 
	 *  a task executor between each stage (unless sync operator invoked). 
	 * A preconfigured LazyReact builder that will be supplied as
	 * input to the function supplied. The user Function should create a LazyFutureStream with any
	 * business logic stages predefined. This method will handle elastic scaling and pooling of Executor
	 * services. User code should call a terminal op on the returned LazyFutureStream
	 * @see Reactive#run(com.aol.simple.react.stream.traits.LazyFutureStream)
	 * 
	 * @param react Function that generates a LazyFutureStream from a LazyReact builder
	 * @return Generated LazyFutureStream
	 */
	default <T> LazyFutureStream<T>  async(Function<LazyReact,LazyFutureStream<T>> react){
		 LazyReact r  =ParallelElasticPools.lazyReact.nextReactor().withAsync(true);
		return  react.apply( r)
					.onFail(e->{ SequentialElasticPools.lazyReact.populate(r); throw e;})
					.peek(i->SequentialElasticPools.lazyReact.populate(r));
		 	
	}
	
	
	/**
	 * Convenience method that runs a LazyFutureStream without blocking the current thread
	 * @param stream to execute
	 */
	default <T> void run(LazyFutureStream<T> stream){
		stream.run();
	}
}
