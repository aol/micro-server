package com.aol.micro.server.javaslang.reactive;


import cyclops.async.LazyReact;
import cyclops.stream.FutureStream;
import cyclops.stream.Spouts;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;



import com.aol.cyclops.types.futurestream.LazyFutureStream;
import com.aol.cyclops.types.stream.reactive.SeqSubscriber;

import javaslang.Value;
import javaslang.collection.Stream;
import javaslang.collection.Traversable;

/**
 * Mixin / Trait for Reactive behaviour via simple-react
 * 
 * @author johnmcclean
 *
 */
public class JavaslangReactive {

	static <T> FutureStream<T> futureStream(Traversable<T> seq, LazyReact lazyReact){
		return lazyReact.fromIterable(seq);
	}
	
	
	
	/**
	 * Publish a reactive-streams publisher to a new Javaslang Stream
	 * 
	 * @param publisher to publish
	 * @return Stream subscribed to publisher
	 */
	public static <T> Stream<T> publishStream(Publisher<T> publisher){

		return Stream.ofAll(Spouts.from(publisher));
	}
	public static <T> void subsribeToValue(Value<T> s, Subscriber<T> sub){

		Javaslang.value(s).subscribe(sub);
		
	}
	/**
	 * Have a reactive-stream subscriber subscribe to a Javaslang Stream.
	 * 
	 * @param s Stream to subscribe to
	 * @param sub Subscriber
	 */
	public static <T> void subsribeToTraversable(Traversable<T> s, Subscriber<T> sub){
		Javaslang.traversable(s).subscribe(sub);
		
	}
	
}
