package com.aol.micro.server.slack.plugin;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.slack.*;
import cyclops.collections.immutable.PersistentSetX;

public class SlackPlugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(SlackConfiguration.class, SlackMessageSender.class);
    }
}
