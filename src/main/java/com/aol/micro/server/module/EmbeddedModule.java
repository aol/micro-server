package com.aol.micro.server.module;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.aol.micro.server.auto.discovery.RestResource;
@Getter
@AllArgsConstructor
public class EmbeddedModule implements Module {

	private final List<Class> restResourceClasses;
	private final String context;
}
