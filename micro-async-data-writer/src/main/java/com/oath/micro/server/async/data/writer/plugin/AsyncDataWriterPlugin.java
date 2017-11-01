package com.aol.micro.server.async.data.writer.plugin;

import java.util.Set;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.async.data.cleaner.ConfigureSchedulingAsyncDataWriter;
import com.aol.micro.server.async.data.writer.ConfigureDataWriter;
import cyclops.collections.mutable.SetX;

public class AsyncDataWriterPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ConfigureSchedulingAsyncDataWriter.class, ConfigureDataWriter.class);
    }

}
