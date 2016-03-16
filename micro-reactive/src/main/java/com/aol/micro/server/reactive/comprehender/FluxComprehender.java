package com.aol.micro.server.reactive.comprehender;


import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import reactor.core.publisher.Flux;

import com.aol.cyclops.types.extensability.Comprehender;

public class FluxComprehender implements Comprehender<Flux> {
	public Class getTargetClass(){
		return Flux.class;
	}
	@Override
	public Object filter(Flux t, Predicate p) {
		return t.filter(p);
	}

	@Override
	public Object map(Flux t, Function fn) {
		return t.map(fn);
	}
	public Flux executeflatMap(Flux t, Function fn){
		return flatMap(t,input -> unwrapOtherMonadTypes(this,fn.apply(input)));
	}
	@Override
	public Flux flatMap(Flux t, Function fn) {
		return t.flatMap(fn);
	}

	@Override
	public boolean instanceOfT(Object apply) {
		return apply instanceof Stream;
	}
	
	@Override
	public Flux empty() {
		return Flux.empty();
	}
	@Override
	public Flux of(Object o) {
		return Flux.just(o);
	}
	public Object resolveForCrossTypeFlatMap(Comprehender comp,Flux apply){
		
		return comp.fromIterator(apply.toIterable().iterator());
	}
	public static <T> T unwrapOtherMonadTypes(Comprehender<T> comp,Object apply){
		
		if(apply instanceof Iterable){
			return (T)Flux.fromIterable((Iterable)apply);
			
		}
		if(apply instanceof BaseStream){
			return (T)Flux.fromStream(StreamSupport.stream(Spliterators.spliteratorUnknownSize(((BaseStream)apply).iterator(), Spliterator.ORDERED),
					false));
		}
		return Comprehender.unwrapOtherMonadTypes(comp,apply);
		
	}
	@Override
	public Flux fromIterator(Iterator o) {
		return Flux.fromIterable(()->o);
	}
	

	

}
