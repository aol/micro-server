package com.aol.micro.server.module;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.aol.micro.server.auto.discovery.RestResource;
@Getter
@AllArgsConstructor
public class EmbeddedModule implements Module {

	private final Class<? extends RestResource> restResourceClass;
	private final String context;
}
