package com.aol.micro.server.slack;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SlackConfiguration {

    private final String webhookUri;

    @Autowired
    public SlackConfiguration(@Value("${slack.webhookUri}") String webhookUri){
        this.webhookUri = webhookUri;
    }
}
