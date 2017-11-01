package com.oath.micro.server.slack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.oath.micro.server.slack.rest.SlackRest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class SlackRestTest {
    
    @Value("${slack.webhookUri}")
    String webHookUri;
    
    @Mock
    private SlackConfiguration slackConfig;
    
    @Mock
    private SlackMessageSender slackMessageSender;
    
    @InjectMocks
    SlackRest slackRest;
    
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(slackConfig.getWebhookUri()).thenReturn(webHookUri);
        Mockito.when(slackMessageSender.postMessageToSlack(Matchers.anyString())).thenReturn(200);
    }
    
    @Test
    public void pingTest() {
        Assert.assertEquals(slackRest.sendMessage("Hello from " + this.getClass().getName()), "OK");
    }
}
