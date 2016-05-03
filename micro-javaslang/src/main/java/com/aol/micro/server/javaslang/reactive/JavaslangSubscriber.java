package com.aol.micro.server.javaslang.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.aol.cyclops.types.stream.reactive.SeqSubscriber;

import javaslang.collection.Array;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import javaslang.collection.Stream;

public class JavaslangSubscriber<T> implements Subscriber<T>{
	/**
	 * A reactive-streams subscriber than can generate Javaslang traversable types
	 * 
	 * @return JavaslangSubscriber
	 */
	public static <T> JavaslangSubscriber<T> subscriber(){
		return new JavaslangSubscriber<T>();
	}
	SeqSubscriber<T> sub = SeqSubscriber.subscriber();

	
	public Stream<T> stream(){
		return Stream.ofAll(sub.stream());
	}
	public List<T> list(){
		return List.ofAll(sub.stream());
	}
	public Array<T> array(){
		return Array.ofAll(sub.stream());
	}
	public Set<T> set(){
		return HashSet.ofAll(sub.stream());
	}
	@Override
	public void onSubscribe(Subscription s) {
		sub.onSubscribe(s);
		
	}
	@Override
	public void onNext(T t) {
		sub.onNext(t);
		
	}
	@Override
	public void onError(Throwable t) {
		sub.onError(t);
		
	}
	@Override
	public void onComplete() {
		sub.onComplete();
	}
	
}