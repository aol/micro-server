package com.oath.micro.server.slack.plugin;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.slack.*;
import cyclops.reactive.collections.mutable.SetX;

import java.util.Set;

public class SlackPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(SlackConfiguration.class, SlackMessageSender.class);
    }
}
