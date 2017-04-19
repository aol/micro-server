package com.aol.micro.server.slack;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SlackMessageSenderTest {
    
    SlackMessageSender slackMessageSender;
    
    @Value("${slack.webhookUri}")
    String webhookUri;
    
    @Before
    public void setup(){
        webhookUri = System.getProperty("slack.webhookUri");
        slackMessageSender = new SlackMessageSender(new SlackConfiguration(webhookUri));
    }

    @Test
    public void postMessageToSlackTest(){
        slackMessageSender = new SlackMessageSender(new SlackConfiguration(webhookUri));
        assertTrue(slackMessageSender.postMessageToSlack("Hello from " + this.getClass().getName()) > 0);
    }
}
