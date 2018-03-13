package com.aol.micro.server.general.exception.mapper;


import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.jooq.lambda.tuple.Tuple.tuple;

@Service
@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	final Logger logger;
	private final boolean showDetails;

	GeneralExceptionMapper(Logger logger, boolean showDetails){
		this.logger = logger;
		this.showDetails = showDetails;
	}

	@Autowired
	public GeneralExceptionMapper(@Value("${micro.general.exception.mapper.details:true}") Boolean showDetails){
		this(LoggerFactory.getLogger(GeneralExceptionMapper.class), showDetails);
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

		Tuple2<String, Status> error = new Tuple2<>(MapOfExceptionsToErrorCodes.INTERNAL_SERVER_ERROR, Status.INTERNAL_SERVER_ERROR);

		Optional<Tuple2<String, Status>> errorFromLookup = find(ex.getClass());

		if (errorFromLookup.isPresent()) {
			error = errorFromLookup.get();

		} else {
			if (ex instanceof javax.ws.rs.WebApplicationException) {
				javax.ws.rs.WebApplicationException rsEx = ((javax.ws.rs.WebApplicationException) ex);
				error = tuple(rsEx.getResponse().getStatusInfo().getReasonPhrase(), Status.fromStatusCode(rsEx.getResponse().getStatus()));

			}
		}
		logger.error(String.format("%s Error id: %s, %s", error.v1(), errorTrackingId, ex.getMessage()), ex);

		Response.ResponseBuilder responseBuilder = Response.status(error.v2()).type(MediaType.APPLICATION_JSON_TYPE);

		if (showDetails) {
			responseBuilder.entity(new ExceptionWrapper(error.v1(), String.format("Error id: %s %s", errorTrackingId, ex.getMessage())));
		} else {
			responseBuilder.entity(new ExceptionWrapper(MapOfExceptionsToErrorCodes.INTERNAL_SERVER_ERROR, errorTrackingId));
		}

		return responseBuilder.build();
	}
}
