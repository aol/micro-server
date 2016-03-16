package com.aol.micro.server.reactive.comprehender;

import java.util.stream.Stream;

import org.reactivestreams.Publisher;

import reactor.core.publisher.Flux;

import com.aol.cyclops.types.extensability.MonadicConverter;
import com.aol.cyclops.types.stream.reactive.SeqSubscriber;

public class PublisherToStreamConverter implements MonadicConverter<Stream> {

	public static int priority = 5;
	public int priority(){
		return priority;
	}
	@Override
	public boolean accept(Object o) {
		return o instanceof Publisher;
	}

	@Override
	public Stream convertToMonadicForm(Object f) {
		
		Publisher p = (Publisher)f;
		if(f instanceof Flux){
			return ((Flux)f).stream();
		}else{
			return Flux.from(p).stream();
		}
		
		
	}

}
