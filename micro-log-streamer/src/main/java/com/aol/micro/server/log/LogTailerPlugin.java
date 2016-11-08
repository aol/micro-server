package com.aol.micro.server.log;

import com.aol.cyclops.data.collections.extensions.persistent.PSetX;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.log.rest.LogStreamer;

public class LogTailerPlugin implements Plugin {

    @Override
    public PSetX<Class> springClasses() {
        return PSetX.of(LogTailer.class, LogStreamer.class);
    }

}
