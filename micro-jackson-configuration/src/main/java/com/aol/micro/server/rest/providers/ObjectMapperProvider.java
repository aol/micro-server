package com.aol.micro.server.rest.providers;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {

	

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return JacksonUtil.getMapper();
	}

	
}
