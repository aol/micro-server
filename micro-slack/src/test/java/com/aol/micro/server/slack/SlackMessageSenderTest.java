package com.aol.micro.server.slack;

import org.junit.*;

public class SlackMessageSenderTest {
    
    private static final String WEB_HOOK_TEST = "https://hooks.slack.com/services/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";
    
    SlackMessageSender slackMessageSender;
    
    @Before
    public void setup(){
        slackMessageSender = new SlackMessageSender(WEB_HOOK_TEST);
    }
    
    @Test
    public void pingTest() {
        Assert.assertEquals(slackMessageSender.slackMessageViaGet("Hello from " + this.getClass().getName()), "OK");
    }
}
