package com.oath.micro.server.rest.jersey;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

import javax.inject.Singleton;


public class AsyncBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AsyncInvocationHandler.AsyncResourceMethodInvocationHandlerProvider.class)
            .to(ResourceMethodInvocationHandlerProvider.class)
            .in(Singleton.class);
    }
}
