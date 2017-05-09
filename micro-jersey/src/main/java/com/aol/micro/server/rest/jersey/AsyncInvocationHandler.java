package com.aol.micro.server.rest.jersey;

import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;
import org.reactivestreams.Publisher;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class AsyncInvocationHandler implements ResourceMethodInvocationHandlerProvider {


    @Override
    public InvocationHandler create(Invocable invocable) {
        Class returnType = invocable.getRawResponseType();
        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(proxy, args);
            }
        };
    }


}