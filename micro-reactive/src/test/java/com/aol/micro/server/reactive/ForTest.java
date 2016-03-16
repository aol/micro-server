package com.aol.micro.server.reactive;

import org.jooq.lambda.tuple.Tuple;
import org.junit.Test;

import reactor.core.publisher.Flux;

import com.aol.cyclops.control.ReactiveSeq;

public class ForTest {

	@Test
	public void forGen(){
		
		For.Publishers.each(Flux.range(1,10),
								i-> ReactiveSeq.iterate(i,a->a+1),
								Tuple::tuple)
								.toListX();
					
	}
}
