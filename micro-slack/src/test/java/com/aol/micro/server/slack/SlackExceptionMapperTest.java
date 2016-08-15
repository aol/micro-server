package com.aol.micro.server.slack;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

public class SlackExceptionMapperTest {
    
    private static final String WEB_HOOK_TEST = "https://hooks.slack.com/services/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";
    
    SlackExceptionMapper mapper;

    @Before
    public void setUp(){
        mapper = new SlackExceptionMapper(new SlackConfiguration(WEB_HOOK_TEST));
    }

    @Test
    public void GenerateErrorOnInternalServerException() {
        assertEquals(mapper.toResponse(mock(RuntimeException.class)).getStatus(), Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}