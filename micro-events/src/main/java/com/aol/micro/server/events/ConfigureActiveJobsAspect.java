package com.aol.micro.server.events;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.eventbus.EventBus;

@Configuration
public class ConfigureActiveJobsAspect {
    @Resource(name = "microserverEventBus")
    private EventBus bus;
    private @Value("${system.logging.max.per.hour:10}") int maxLoggingCapacity;
    private @Value("${system.request.capture:true}") boolean requestCapture;

    @Bean
    public JobsBeingExecuted microEventJobsBeingExecuted() {
        return new JobsBeingExecuted(
                                     bus, maxLoggingCapacity);
    }

    @Bean
    public RequestTypes microEventRequestTypes() {
        RequestsBeingExecuted def = this.microEventRequestsBeingExecuted();
        RequestTypes types = new RequestTypes(
                                              bus);
        types.getMap()
             .put(def.getType(), def);
        return types;
    }

    @Bean
    public RequestsBeingExecuted microEventRequestsBeingExecuted() {
        return new RequestsBeingExecuted(
                                         bus, requestCapture);
    }

}
