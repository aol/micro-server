package com.aol.micro.server.auto.discovery;

import javax.servlet.Filter;

import com.aol.cyclops.control.Xor;

public interface AutoFilterConfiguration extends Filter, FilterConfiguration{

	@Override
	default Xor<Class<? extends Filter>,Filter> getFilter(){
		return Xor.primary(this);
	}
}
