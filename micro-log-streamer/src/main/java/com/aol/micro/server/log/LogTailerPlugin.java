package com.aol.micro.server.log;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.log.rest.LogStreamer;
import cyclops.collections.immutable.PSetX;

public class LogTailerPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(LogTailer.class, LogStreamer.class);
    }

}
