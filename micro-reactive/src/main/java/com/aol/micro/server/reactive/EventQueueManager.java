package com.aol.micro.server.reactive;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import cyclops.async.Future;
import cyclops.async.LazyReact;
import cyclops.async.Pipes;
import cyclops.async.adapters.QueueFactory;
import cyclops.control.Eval;
import cyclops.control.Maybe;
import cyclops.function.FluentFunctions;
import cyclops.stream.FutureStream;
import cyclops.stream.ReactiveSeq;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Class for pusing information across threads to various consumers.
 * Push data onto a queue on one thread for a Stream or Consumer on another to react to.
 * 
 * @author johnmcclean
 *
 * @param <T>
 */
@AllArgsConstructor(access=AccessLevel.PRIVATE)
public class EventQueueManager<T> {
		public final static Supplier<LazyReact> io = FluentFunctions.of(()->new LazyReact(100,100))
													   .memoize();
		public static <T> EventQueueManager<T> of(Executor ex, QueueFactory<T> factory){
			return new EventQueueManager<>(ex,factory);
		}
		private final Pipes<String,T> pipes = Pipes.of();
		private final Executor ex;
		private final QueueFactory<T> factory;
		
		/**
		 * Push data onto the named queue.
		 * 
		 * @param key Queue name
		 * @param value Data to push
		 */
		public void push(String key, T value){
			pipes.push(key,value);
		}
		/**
		 * React to any data pushed onto the named Queue with the supplied consumer.
		 * 
		 * @param key Queu name
		 * @param reactor Consumer to react to new data
		 */
		public void forEach(String key,Consumer<? super T> reactor){
			if(!pipes.get(key).isPresent())
				pipes.register(key, factory.build());
			pipes.reactiveSeq(key)
				 .get().foldFuture(ex,
				 s->s.forEach(Long.MAX_VALUE,reactor));
		}
		/**
		 * @param key Register a new queue with supplied key
		 */
		public void register(String key){
			pipes.register(key, factory.build());
		}
		/**
		 * Asynchronously extract a single data point from the named Queue.
		 * 
		 * 
		 * @param key Queue name
		 * @param ex Executor to run on
		 * @return FutureW that will eventually have data from the Queue
		 */
		public Future<T> future(String key, Executor ex){
			return pipes.oneOrErrorAsync(key, ex);
		}
		/**
		 * Asynchronously extract a single data point from the named Queue.
		 * 
		 * 
		 * @param key Queue name
		 * @param ex Executor to run on
		 * @return Mono that will eventually have data from the Queue
		 */
		public Mono<T> mono(String key,Executor ex){
			return Mono.fromFuture(pipes.oneOrErrorAsync(key, ex)
						.getFuture());
		}
		
		/**
		 * Generate a Reactor Stream (Flux) from the supplied Queue name.
		 * Any data pushed to the Queue will pass on to the returned Flux.
		 * 
		 * @param key Queue name
		 * @return Flux which will recieve data from the Queue
		 */
		public Flux<T> flux(String key){	
			if(!pipes.get(key).isPresent())
				pipes.register(key, factory.build());
			return Flux.from(pipes.reactiveSeq(key)
				     			  .get());
		}
		/**
		 * Generate a cyclops-react Stream (ReactiveSeq) from the supplied Queue name.
		 * Any data pushed to the Queue will pass on to the returned ReactiveSeq.
		 * 
		 * @param key Queue name
		 * @return ReactiveSeq which will recieve data from the Queue
		 */
		public ReactiveSeq<T> stream(String key){
			if(!pipes.get(key).isPresent())
				pipes.register(key, factory.build());
			return pipes.reactiveSeq(key)
				     	.get();
		}
		/**
		 * Lazily extract a single value from the named Queue.
		 * Maybe will be none if the Queue is closed, otherwise will retrieve the next value.
		 * 
		 * @param key Queue name
		 * @return Maybe with next data point
		 */
		public Maybe<T> maybe(String key){
			if(!pipes.get(key).isPresent())
				pipes.register(key, factory.build());
			return pipes.oneValue(key);
		}
		/**
		 * Lazily extract a single value from the named Queue.
		 * Eval will be contain null if the Queue is closed, otherwise will retrieve the next value.
		 * 
		 * @param key Queue name
		 * @return Eval with next data point with next data point
		 */
		public Eval<T> lazy(String key){
			if(!pipes.get(key).isPresent())
				pipes.register(key, factory.build());
			return pipes.nextOrNull(key);
		}
		/**
		 * Generate a Stream of Futures from the supplied key name
		 * 
		 * @param key Queue name
		 * @return Stream of futures
		 */
		public FutureStream<T> ioFutureStream(String key){
			if(!pipes.get(key).isPresent())
				pipes.register(key, factory.build());
			return pipes.futureStream(key, io.get())
					    .get();
		}
		
		
}