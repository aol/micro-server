package com.oath.micro.server.async.data.writer.plugin;

import java.util.Set;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.async.data.cleaner.ConfigureSchedulingAsyncDataWriter;
import com.oath.micro.server.async.data.writer.ConfigureDataWriter;
import cyclops.reactive.collections.mutable.SetX;

public class AsyncDataWriterPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ConfigureSchedulingAsyncDataWriter.class, ConfigureDataWriter.class);
    }

}
