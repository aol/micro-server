package com.aol.micro.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.aol.micro.server.rest.RestResource;
@Getter
@AllArgsConstructor
public class EmbeddedModule implements Module {

	private final Class<? extends RestResource> restResourceClass;
	private final String context;
}
