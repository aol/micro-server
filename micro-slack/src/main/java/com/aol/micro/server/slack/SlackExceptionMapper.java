package com.aol.micro.server.slack;

import com.aol.micro.server.general.exception.mapper.*;
import java.util.UUID;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.*;
import javax.ws.rs.ext.*;

@Provider
public class SlackExceptionMapper implements ExceptionMapper<Exception> {

	private SlackMessageSender slackMessageSender;
    
    SlackExceptionMapper(SlackConfiguration slackConfiguration){
        slackMessageSender = new SlackMessageSender(slackConfiguration);
    }
	
	@Override
	public Response toResponse(final Exception ex) {

		final String errorTrackingId = UUID.randomUUID().toString();
		ExceptionWrapper wrappedException = new ExceptionWrapper(errorTrackingId, String.format("Error id: %s %s", errorTrackingId, ex.getMessage()));
		
		slackMessageSender.postMessageToSlack(wrappedException.toString());

		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(wrappedException)
				.type(MediaType.APPLICATION_JSON_TYPE).build();
	}
}