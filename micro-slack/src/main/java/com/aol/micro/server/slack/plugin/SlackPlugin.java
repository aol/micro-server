package com.aol.micro.server.slack.plugin;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.slack.*;

public class SlackPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(SlackConfiguration.class, SlackMessageSender.class);
    }
}
