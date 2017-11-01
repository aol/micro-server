package com.aol.micro.server.log;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.log.rest.LogStreamer;
import cyclops.collections.immutable.PersistentSetX;
import cyclops.collections.mutable.SetX;

import java.util.Set;

public class LogTailerPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(LogTailer.class, LogStreamer.class);
    }

}
