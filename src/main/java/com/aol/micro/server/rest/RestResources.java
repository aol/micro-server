package com.aol.micro.server.rest;

import java.util.Collection;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;


public class RestResources {

	@Getter
	private final ImmutableList<RestResource> allResources;

	
	public RestResources(Collection<RestResource> allResources) {
		this.allResources = ImmutableList.copyOf(allResources);
	}
	
	public RestResources(){
		allResources = ImmutableList.of();
	}

}
