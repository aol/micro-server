package com.aol.micro.server.slack.plugin;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.slack.*;
import com.aol.micro.server.slack.rest.SlackRest;

public class SlackPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(SlackRest.class, SlackConfiguration.class);
    }
}
