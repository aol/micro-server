package com.aol.micro.server.reactive;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.aol.cyclops.control.AnyM;
import com.aol.cyclops.control.FutureW;
import com.aol.cyclops.control.LazyReact;
import com.aol.cyclops.control.ReactiveSeq;
import com.aol.cyclops.types.anyM.AnyMSeq;
import com.aol.cyclops.types.futurestream.LazyFutureStream;
import com.aol.cyclops.types.stream.reactive.SeqSubscriber;
import com.aol.cyclops.types.stream.reactive.ValueSubscriber;

/**
 * Type conversion tier for cyclops-react and Reactor.
 * Note both cyclops-react and Reactor data types can generally be constructed from another
 * reactive-streams publisher.
 * e.g.
 * <pre>
 * {@code 
 *  ListX.fromPublisher(Flux.just(1,2,3));
 *  Flux.from(SetX.of(1,2,3));
 * }</pre>
 * 
 * 
 * @author johnmcclean
 *
 */
public interface CyclopsReactor {
	
	static <T> AnyMSeq<T> from(Publisher<? extends T> t){
		return (AnyMSeq<T>)AnyM.ofSeq(Flux.from(t));
	}
	
	static <T> SeqSubscriber<T> fromFlux(Flux<T> flux){
		SeqSubscriber<T> seqSubscriber = SeqSubscriber.subscriber();
		flux.subscribe(seqSubscriber);
		return seqSubscriber;
	}
	static <T> ValueSubscriber<T> fromMono(Mono<T> mono){
		
		ValueSubscriber<T> valueSubscriber = ValueSubscriber.subscriber();
		mono.subscribe(valueSubscriber);
		return valueSubscriber;
	}
	static <T> ReactiveSeq<T> reactiveSeq(Flux<T> flux){
		return fromFlux(flux)
					.stream();
	}
	static <T> LazyFutureStream<T> futureStream(Flux<T> flux, LazyReact lazyReact){
		return fromFlux(flux)
					.toFutureStream(lazyReact);
	}
	
	static <T> FutureW<T> futureW(Mono<T> mono){
		return FutureW.of(mono.toFuture());
	}
	
	
}
