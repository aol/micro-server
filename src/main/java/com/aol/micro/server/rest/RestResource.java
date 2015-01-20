package com.aol.micro.server.rest;

import java.util.Optional;

import com.aol.micro.server.Module;

public interface RestResource {

	default Optional<Module> getModule() {
		return Optional.empty();
	}

}
