package com.aol.micro.server.reactive;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.aol.cyclops.control.Eval;
import com.aol.cyclops.control.FluentFunctions;
import com.aol.cyclops.control.FutureW;
import com.aol.cyclops.control.LazyReact;
import com.aol.cyclops.control.Maybe;
import com.aol.cyclops.control.Pipes;
import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.data.async.QueueFactory;
import com.aol.cyclops.types.futurestream.LazyFutureStream;

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
		
		public void push(String key, T value){
			pipes.push(key,value);
		}
		public void forEach(String key,Consumer<? super T> reactor){
				pipes.register(key, factory.build());
				pipes.reactiveSeq(key)
				     .get()
				     .futureOperations(ex)
					 .forEach(reactor);
		}
		public void register(String key){
			pipes.register(key, factory.build());
		}
		public FutureW<T> future(String key,Executor ex){
			return pipes.oneOrErrorAsync(key, ex);
		}
		public Mono<T> mono(String key,Executor ex){
			return Mono.fromCompletableFuture(pipes.oneOrErrorAsync(key, ex)
						.getFuture());
		}
		public ReactiveSeq<T> streams(String...keys){
			return ReactiveSeq.of(keys)
					   .map(k->stream(k))
					   .flatMapPublisher(i->i);
		}
		public Flux<T> fluxes(String...keys){	
			return Flux.just(keys)
					   .map(k->stream(k))
					   .flatMap(i->i);
		}
		public Flux<T> flux(String key){	
			pipes.register(key, factory.build());
			return Flux.from(pipes.reactiveSeq(key)
				     			  .get());
		}
		public ReactiveSeq<T> stream(String key){
			
			pipes.register(key, factory.build());
			return pipes.reactiveSeq(key)
				     	.get();
		}
		public  Maybe<T> maybe(String key){
			pipes.register(key, factory.build());
			return pipes.oneValue(key);
		}
		public Eval<Maybe<T>> lazyMaybe(String key){
			pipes.register(key, factory.build());
			return pipes.nextValue(key);
		}
		public Eval<T> lazy(String key){
			
			pipes.register(key, factory.build());
			return pipes.nextOrNull(key);
		}
		public LazyFutureStream<T> ioFutureStream(String key){
			
			pipes.register(key, factory.build());
			return pipes.futureStream(key, io.get())
					    .get();
		}
		
		
}