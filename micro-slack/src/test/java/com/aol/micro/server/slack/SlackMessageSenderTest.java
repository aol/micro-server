package com.aol.micro.server.slack;

import static org.junit.Assert.assertTrue;

import org.junit.*;

public class SlackMessageSenderTest {
    
    private static final String WEB_HOOK_TEST = "https://hooks.slack.com/services/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";
    
    SlackMessageSender slackMessageSender;
    
    @Before
    public void setup(){
        slackMessageSender = new SlackMessageSender(new SlackConfiguration(WEB_HOOK_TEST));
    }

    @Test
    public void postMessageToSlackTest(){
        assertTrue(slackMessageSender.postMessageToSlack("Hello from " + this.getClass().getName()) > 0);
    }
}
