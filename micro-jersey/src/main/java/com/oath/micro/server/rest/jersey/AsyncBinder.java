package com.oath.micro.server.rest.jersey;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;

import javax.inject.Singleton;


public class AsyncBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AsyncDispatcher.AsyncDispatcherProvider.class).to(
                ResourceMethodDispatcher.Provider.class).in(Singleton.class)
                .ranked(1);
    }
}