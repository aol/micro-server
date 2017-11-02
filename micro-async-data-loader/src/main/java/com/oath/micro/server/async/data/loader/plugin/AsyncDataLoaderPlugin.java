package com.oath.micro.server.async.data.loader.plugin;

import java.util.Set;


import com.oath.micro.server.Plugin;
import com.oath.micro.server.async.data.loader.ConfigureSchedulingAsyncDataLoader;
import cyclops.collections.mutable.SetX;

public class AsyncDataLoaderPlugin implements Plugin {

    @Override
    public Set<Class> springClasses() {
        return SetX.of(ConfigureSchedulingAsyncDataLoader.class);
    }

}
