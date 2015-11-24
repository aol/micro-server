package com.aol.micro.server.javaslang.reactive;

import java.util.Optional;
import java.util.concurrent.Executor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import javaslang.collection.Stream;
import javaslang.control.Failure;
import javaslang.control.Success;
import javaslang.control.Try;

import com.aol.cyclops.javaslang.FromSimpleReact;
import com.aol.cyclops.javaslang.reactivestreams.JavaslangReactiveStreamsPublisher;
import com.aol.cyclops.javaslang.reactivestreams.JavaslangReactiveStreamsSubscriber;
import com.aol.simple.react.async.Adapter;
import com.aol.simple.react.async.pipes.LazyReactors;
import com.aol.simple.react.async.pipes.Pipes;
import com.aol.simple.react.stream.lazy.LazyReact;
import com.aol.simple.react.stream.traits.LazyFutureStream;

/**
 * Mixin / Trait for Reactive behaviour via simple-react
 * 
 * @author johnmcclean
 *
 */
public interface JavaslangReactive {
	/**
	 * Add a value to an simple-react Async.Queue
	 * 
	 * @see Pipes#register(Object, com.aol.simple.react.async.Adapter)
	 * 
	 * @param key : identifier for registered Queue
	 * @param value : value to add to Queue
	 */
	default<K,V> Try<Boolean> enqueue(K key,V value){
		Optional<Adapter<V>> queue = JavaslangPipes.get(key);
		queue.map(adapter -> adapter.offer(value));
	
		
		return queue.isPresent() ? new Success<>(true) : 
						new Failure<>(new RuntimeException("Missing queue for key : " + key.toString()));
		
		
	}
	/**
	 * Publish a reactive-streams publisher to a new Javaslang Stream
	 * 
	 * @param publisher to publish
	 * @return Stream subscribed to publisher
	 */
	default <T> Stream<T> publish(Publisher<T> publisher){
		JavaslangReactiveStreamsSubscriber<T> sub = new JavaslangReactiveStreamsSubscriber<>();
		publisher.subscribe(sub);
		return sub.getStream();
	}
	/**
	 * Have a reactive-stream subscriber subscribe to a Javaslang Stream.
	 * 
	 * @param s Stream to subscribe to
	 * @param sub Subscriber
	 */
	default <T> void subsribe(Stream<T> s, Subscriber<T> sub){
		JavaslangReactiveStreamsPublisher<T> pub = JavaslangReactiveStreamsPublisher.ofSync(s);
		pub.subscribe(sub);
	}
	/**
	 * Have a reactive-stream subscriber subscribe to a Javaslang Stream asynchronously.
	 * 
	 * @param s Stream to subscribe to
	 * @param sub Subscriber
	 * @param exec task executor for async operations
	 */
	default <T> void subsribe(Stream<T> s, Subscriber<T> sub,Executor exec){
		JavaslangReactiveStreamsPublisher<T> pub = JavaslangReactiveStreamsPublisher.ofAsync(s,exec);
		pub.subscribe(sub);
	}
	default <K,T> LazyFutureStream<T> ioFutureStream(K key){
		return JavaslangPipes.futureStreamIOBound(key);
	}
	default <K,T> Stream<T> sequentialStream(K key){
		return JavaslangPipes.<T>stream(key);
	}
	default <K,T> LazyFutureStream<T> cpuFutureStream(K key){
		return JavaslangPipes.futureStreamCPUBound(key);
	}
	default  LazyReact ioStreamBuilder(){
		return LazyReactors.ioReact;
	}
	
	default LazyReact cpuStreamBuilder(){
		return LazyReactors.cpuReact;
	}
	
	default <T> Stream<T> switchToSequential(LazyFutureStream<T> stream){
		return FromSimpleReact.fromSimpleReact(stream);
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
