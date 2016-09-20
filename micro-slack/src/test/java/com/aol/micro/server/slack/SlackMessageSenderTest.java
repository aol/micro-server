package com.aol.micro.server.slack;

import static org.junit.Assert.assertTrue;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/context.xml")
public class SlackMessageSenderTest {
    
    SlackMessageSender slackMessageSender;
    
    @Value("${slack.webhookUri}")
    String webHookUri;
    
    @Before
    public void setup(){
        System.out.println(webHookUri);
        slackMessageSender = new SlackMessageSender(new SlackConfiguration(webHookUri));
    }

    @Test
    public void postMessageToSlackTest(){
        assertTrue(slackMessageSender.postMessageToSlack("Hello from " + this.getClass().getName()) != -1);
    }
}
