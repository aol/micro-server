package com.aol.micro.server.log;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.log.rest.LogStreamer;
import cyclops.collections.immutable.PersistentSetX;

public class LogTailerPlugin implements Plugin {

    @Override
    public PersistentSetX<Class> springClasses() {
        return PersistentSetX.of(LogTailer.class, LogStreamer.class);
    }

}
