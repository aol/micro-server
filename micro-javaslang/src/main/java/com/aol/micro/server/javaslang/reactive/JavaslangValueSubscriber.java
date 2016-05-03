package com.aol.micro.server.javaslang.reactive;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.aol.cyclops.control.Xor;
import com.aol.cyclops.javaslang.FromJDK;
import com.aol.cyclops.types.stream.reactive.ValueSubscriber;

import javaslang.control.Either;
import javaslang.control.Option;
import javaslang.control.Try;

public class JavaslangValueSubscriber<T> implements Subscriber<T>{
	
	/**
	 * A reactive-streams subscriber that can generate a Javaslang value type
	 * 
	 * @return JavaslangValueSubscriber
	 */
	public static <T> JavaslangValueSubscriber<T> subscriber(){
		return new JavaslangValueSubscriber<T>();
	}
	ValueSubscriber<T> sub = ValueSubscriber.subscriber();

	public Option<T> option(){
		return FromJDK.option(sub.toOptional());
	}
	public Try<T> tryValue(){
		Xor<Throwable,T> xor = sub.toXor();
		return xor.visit(e->Try.failure(e),t->Try.success(t));
	}
	public Either<Throwable,T> either(){
		Xor<Throwable,T> xor = sub.toXor();
		return xor.visit(e->Either.left(e),t->Either.right(t));
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