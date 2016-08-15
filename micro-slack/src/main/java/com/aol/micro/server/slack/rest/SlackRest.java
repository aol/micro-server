package com.aol.micro.server.slack.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.aol.micro.server.auto.discovery.Rest;
import com.aol.micro.server.slack.SlackConfiguration;
import com.aol.micro.server.slack.SlackMessageSender;

@Rest
@Path("/slack")
public class SlackRest {
    
    SlackMessageSender slackMessageSender;
    
    @Autowired
    public SlackRest(SlackConfiguration slackConfiguration){
        slackMessageSender = new SlackMessageSender(slackConfiguration);
    }
    
    @GET
    @Path("/message")
    public String slackMessageViaGet(@QueryParam("txt") final String msg) {
        slackMessageSender.postMessageToSlack(msg);
        return "OK";
    }
}
