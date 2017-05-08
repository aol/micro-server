package com.aol.micro.server.async.data.loader.plugin;

import java.util.Set;


import com.aol.micro.server.Plugin;
import com.aol.micro.server.async.data.loader.ConfigureSchedulingAsyncDataLoader;
import cyclops.collections.SetX;

public class AsyncDataLoaderPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ConfigureSchedulingAsyncDataLoader.class);
    }

}
