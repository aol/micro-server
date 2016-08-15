package com.aol.micro.server.slack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.slack.rest.SlackRest;

public class SlackRestTest {
    
    private static final String WEB_HOOK_TEST = "https://hooks.slack.com/services/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";
    
    SlackRest slackRest;
    
    @Before
    public void setup(){
        slackRest = new SlackRest(new SlackConfiguration(WEB_HOOK_TEST));
    }
    
    @Test
    public void pingTest() {
        Assert.assertEquals(slackRest.slackMessageViaGet("Hello from " + this.getClass().getName()), "OK");
    }
}
