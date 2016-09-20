package com.aol.micro.server.slack.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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
    
    @POST
    @Path("/message")
    @Consumes("application/json")
    public String sendMessage(final String msg) {
        try{
            slackMessageSender.postMessageToSlack(msg);
        }
        catch(IOException e){
            return "INTERNAL_ERROR";
        }
        return "OK";
    }
}
