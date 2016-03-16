package com.aol.micro.server.reactive;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.aol.cyclops.Reducers;
import com.aol.cyclops.control.FutureW;
import com.aol.cyclops.control.LazyReact;
import com.aol.cyclops.control.Maybe;
import com.aol.cyclops.data.collections.extensions.persistent.PStackX;
import com.aol.cyclops.data.collections.extensions.standard.ListX;
import com.aol.cyclops.types.anyM.AnyMSeq;

public class CyclopsReactorTest {

	@Test
	public void testFrom() {
		AnyMSeq<String> flux = CyclopsReactor.from(Flux.just("hello","world","c"));
		Flux<Integer> f = null;
		f.map(PStackX::of).reduce(Reducers.toPStackX());
		f.collect(ListX::empty, ListX::add);
		System.out.println(Flux.just("hello","world")
							   .map(a->a+"!")
							   .toList().get());
	

		String res = flux.map(a->a+"!")
						.join("-");
			
		assertThat(res,equalTo("hello!-world!-c!"));	
	}

	@Test
	public void testFromFlux() {
		
		assertThat(CyclopsReactor.fromFlux(Flux.just(1,2,3))
					 .toListX(),equalTo(ListX.of(1,2,3)));
	}

	@Test
	public void testFromMono() {
		assertThat(CyclopsReactor.fromMono(Mono.just(2))
				 .toMaybe(),equalTo(Maybe.just(2)));
	}

	@Test
	public void testReactiveSeq() {
		assertThat(CyclopsReactor.reactiveSeq(Flux.just(1,2,3))
				 .toListX(),equalTo(ListX.of(1,2,3)));
	}

	@Test
	public void testFutureStream() {
		assertThat(CyclopsReactor.futureStream(Flux.just(1,2,3), new LazyReact())
				 .toListX(),equalTo(ListX.of(1,2,3)));
	}

	@Test
	public void testFutureW() {
		assertThat(CyclopsReactor.futureW(Mono.just(2))
				 .toMaybe(),equalTo(FutureW.ofResult(2)));
	}

}
