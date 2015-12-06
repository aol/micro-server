package com.aol.micro.server.validation;

import java.util.function.Consumer;

import lombok.AllArgsConstructor;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import com.aol.cyclops.validation.CumulativeValidator;
import com.aol.cyclops.validation.FailedResult;
import com.aol.cyclops.validation.ValidationResults;

@AllArgsConstructor
public class Validator<T,R,E> {

	private final CumulativeValidator<T,R,E> validationRules;
	
	public Valid<R,E> isValid(T input){
		ValidationResults<R,E> result = validationRules.accumulate(input);
		return new Valid<>(result
						.getResults()
						.stream()
						.filter(t-> t instanceof FailedResult)
						.findFirst().isPresent(),result);
	}
	
	
	
}
