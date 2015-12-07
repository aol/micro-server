package com.aol.micro.server.general.exception.mapper;

import java.io.EOFException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.cyclops.monad.AnyM;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class MapOfExceptionsToErrorCodes {
	
	public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
	public static final String EMPTY_REQUEST = "EMPTY_REQUEST";
	public static final String JSON_PROCESSING_EXCEPTION = "JSON_PROCESSING_EXCEPTION";
	public static final String NOT_FOUND = "NOT_FOUND";
	private static final String UNSUPPORTED_MEDIA_TYPE = "UNSUPPORTED_MEDIA_TYPE";

	
	private volatile static LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> mapOfExceptionsToErrorCodes = createMap();

	private static LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> createMap(){
			LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> mapOfExceptionsToErrorCodes = new LinkedHashMap<>();
			mapOfExceptionsToErrorCodes.put(EOFException.class, new Tuple2<String, Status>(EMPTY_REQUEST, Status.BAD_REQUEST));
			mapOfExceptionsToErrorCodes.put(JsonProcessingException.class, new Tuple2<String, Status>(JSON_PROCESSING_EXCEPTION, Status.BAD_REQUEST));
			return mapOfExceptionsToErrorCodes;
	
	}
	private volatile static Optional<ExtensionMapOfExceptionsToErrorCodes> extensions = Optional.empty();
	
	public static Map<Class<? extends Exception>, Tuple2<String, Status>> getMergedMappings(){
		Map<Class<? extends Exception>, Tuple2<String, Status>> result = new LinkedHashMap<>();
		result.putAll(mapOfExceptionsToErrorCodes);
		AnyM.fromOptional(extensions)
			.peek(ext->result.putAll(ext.getErrorMappings()));
		return result;
	}
	

	
	//@Autowired(required=false)
	public MapOfExceptionsToErrorCodes(){
		
	}
	@Autowired(required=false)
	public MapOfExceptionsToErrorCodes(ExtensionMapOfExceptionsToErrorCodes ext){
		MapOfExceptionsToErrorCodes.extensions = Optional.ofNullable(ext);
	}
	
}
