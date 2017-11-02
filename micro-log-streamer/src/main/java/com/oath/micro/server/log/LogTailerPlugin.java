package com.oath.micro.server.log;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.log.rest.LogStreamer;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class LogTailerPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(LogTailer.class, LogStreamer.class);
    }

}
