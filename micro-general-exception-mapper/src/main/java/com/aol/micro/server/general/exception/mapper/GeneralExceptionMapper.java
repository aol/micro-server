package com.aol.micro.server.general.exception.mapper;


import static org.jooq.lambda.tuple.Tuple.tuple;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	final Logger logger;
	
	 GeneralExceptionMapper(Logger logger){
		 this.logger = logger;
	 }
	 
	 public GeneralExceptionMapper(){
		 this.logger  = LoggerFactory.getLogger(GeneralExceptionMapper.class);;
	 }

	
	Map<Class<? extends Exception>, Tuple2<String, Status>> mapOfExceptionsToErrorCodes = MapOfExceptionsToErrorCodes.getMergedMappings();

	private Optional<Tuple2<String, Status>> find(Class<? extends Exception> c) {
		for (Map.Entry<Class<? extends Exception>, Tuple2<String, Status>> entry : this.mapOfExceptionsToErrorCodes.entrySet()) {
			if (entry.getKey().isAssignableFrom(c)) {
				return Optional.ofNullable(entry.getValue());
			}
		}
		return Optional.empty();
	}

	@Override
	public Response toResponse(final Exception ex) {

		final String errorTrackingId = UUID.randomUUID().toString();

		Tuple2<String, Status> error = new Tuple2<String, Status>(MapOfExceptionsToErrorCodes.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR);

		Optional<Tuple2<String, Status>> errorFromLookup = find(ex.getClass());

		if (errorFromLookup.isPresent()) {
			error = errorFromLookup.get();

		} else {
			if(ex instanceof javax.ws.rs.WebApplicationException){
				javax.ws.rs.WebApplicationException rsEx = ((javax.ws.rs.WebApplicationException)ex);
				error = tuple(rsEx.getResponse().getStatusInfo().getReasonPhrase(),Status.fromStatusCode(rsEx.getResponse().getStatus()));
				
			}
			logger.error( String.format("%s Error id: %s", error.v1(), errorTrackingId) + ex.getMessage(), ex);
		}
		logger.warn( String.format("%s Error id: %s", error.v1(), errorTrackingId));

		return Response.status(error.v2())
				.entity(new ExceptionWrapper(error.v1(), String.format("Error id: %s %s", errorTrackingId, ex.getMessage())))
				.type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}
