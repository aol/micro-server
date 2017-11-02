package com.oath.micro.server.auto.discovery;

import cyclops.control.Either;

import javax.servlet.Filter;



public interface AutoFilterConfiguration extends Filter, FilterConfiguration{

	@Override
	default Either<Class<? extends Filter>,Filter> getFilter(){
		return Either.right(this);
	}
}
