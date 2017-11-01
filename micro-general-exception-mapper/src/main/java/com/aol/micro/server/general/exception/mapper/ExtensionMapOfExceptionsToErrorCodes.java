package com.aol.micro.server.general.exception.mapper;

import java.util.LinkedHashMap;

import javax.ws.rs.core.Response.Status;

import cyclops.data.tuple.Tuple2;

/**
 * Create a Spring that implements this interface to define your own mappings between 
 * Exceptions and Error Codes
 * 
 * @author johnmcclean
 * 
 * <pre>
 * {@code 
 *  Default values are 
 *  Map<Class<? extends Exception>, Tuple2<String, Status>> mapOfExceptionsToErrorCodes = new LinkedHashMap<>();

	{
		mapOfExceptionsToErrorCodes.put(EOFException.class, new Tuple2<String, Status>(EMPTY_REQUEST, Status.BAD_REQUEST));
		mapOfExceptionsToErrorCodes.put(JsonProcessingException.class, new Tuple2<String, Status>(JSON_PROCESSING_EXCEPTION, Status.BAD_REQUEST));
	}
 * }
 * </pre>
 * 
 * 
 * 
 *
 */
public interface ExtensionMapOfExceptionsToErrorCodes {

	public LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> getErrorMappings();
}
