package com.aol.micro.server.slack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Matchers.*;

import com.aol.micro.server.slack.rest.SlackRest;

public class SlackRestTest {
    
    @Mock
    private SlackConfiguration slackConfig;
    
    @Mock
    private SlackMessageSender slackMessageSender;
    
    @InjectMocks
    SlackRest slackRest;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(slackConfig.getWebhookUri()).thenReturn("webhook.slack.com");
        Mockito.when(slackRest.sendMessage("Hola")).thenReturn("OK");
    }
    
    @Test
    public void pingTest() {
        Assert.assertEquals(slackRest.sendMessage("Hello"), "OK");
    }
}