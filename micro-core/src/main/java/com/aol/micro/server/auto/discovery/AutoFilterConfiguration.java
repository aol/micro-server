package com.aol.micro.server.auto.discovery;

import cyclops.control.Xor;

import javax.servlet.Filter;



public interface AutoFilterConfiguration extends Filter, FilterConfiguration{

	@Override
	default Xor<Class<? extends Filter>,Filter> getFilter(){
		return Xor.primary(this);
	}
}
