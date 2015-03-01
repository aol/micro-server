package com.aol.micro.server.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor 
public abstract class RemoveEvent<T>{
	@Getter
	private final T data;
} 