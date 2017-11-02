package com.oath.micro.server.slack.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.slack.SlackConfiguration;
import com.oath.micro.server.slack.SlackMessageSender;

@Rest
@Path("/slack")
public class SlackRest {
    
    SlackMessageSender slackMessageSender;
    
    @Autowired
    public SlackRest(SlackConfiguration slackConfiguration){
        slackMessageSender = new SlackMessageSender(slackConfiguration);
    }
    
    @POST
    @Path("/message")
    @Consumes("application/json")
    public String sendMessage(final String msg) {
        slackMessageSender.postMessageToSlack(msg);
        return "OK";
    }
}
