package com.aol.micro.server.slack.plugin;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.slack.*;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class SlackPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(SlackConfiguration.class, SlackMessageSender.class);
    }
}
