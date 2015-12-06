package com.aol.micro.server.validation;

import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;

import com.aol.cyclops.sequence.SequenceM;
import com.aol.cyclops.validation.ValidationResults;
@AllArgsConstructor
public class Valid<R,E> {

	public final boolean valid;
	public final ValidationResults<R,E> results;
	
	public void success(Consumer<R> accept){
		if(valid)
			results.getResults().stream().forEach(r-> accept.accept((R)r.success().get()));
	}
	public void fail(Consumer<E> c){
		if(!valid)
			results.getResults().stream().forEach(r-> c.accept((E)r.failure().get()));
	}
	
	public List<E> failureList(){
		return SequenceM.fromIterable(results.getResults())
						.filter(v->v.failure().isPresent())
						.map(r->(E)r.failure().get())
						.toList();
	}
	public List<R> successList(){
		return SequenceM.fromIterable(results.getResults())
						.filter(v->v.success().isPresent())
						.map(r->(R)r.success().get())
						.toList();
	}
	
}
