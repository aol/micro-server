package com.aol.micro.server.slack;

import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SlackExceptionMapper implements ExceptionMapper<Exception> {

    private SlackMessageSender slackMessageSender;

    SlackExceptionMapper(SlackConfiguration slackConfiguration) {
        slackMessageSender = new SlackMessageSender(slackConfiguration);
    }

    @Override
    public Response toResponse(final Exception ex) {

        final String errorTrackingId = UUID.randomUUID().toString();

        String message = String.format("Error id: %s %s", errorTrackingId, ex.getMessage());

        slackMessageSender.postMessageToSlack(message);

        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex).type(MediaType.APPLICATION_JSON_TYPE).build();
    }
}