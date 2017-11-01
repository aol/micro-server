package com.oath.micro.server.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oath.micro.server.events.JobsBeingExecuted;
import com.oath.micro.server.events.RequestTypes;

@Configuration
public class ConfigureResources {

    @Autowired
    private RequestTypes activeQueries;

    @Bean
    public ActiveResource activeResource(
            @Qualifier("microEventJobsBeingExecuted") JobsBeingExecuted jobsBeingExecuted) {
        return new ActiveResource(
                                  activeQueries, jobsBeingExecuted);
    }

    @Bean
    public ManifestResource manifest() {
        return new ManifestResource();
    }
}
