package com.oath.micro.server.rest.jersey;

import cyclops.reactive.Spouts;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class AsyncInvocationHandler implements InvocationHandler {

    @Inject
    protected Provider<AsyncContext> contextProvider;

    @Inject
    protected Provider<ContainerRequestContext> containerRequestProvider;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        final AsyncContext context =  contextProvider.get();
        if (!context.suspend()) {
            throw new ProcessingException(LocalizationMessages.ERROR_SUSPENDING_ASYNC_REQUEST());
        }
        final ContainerRequestContext requestContext = containerRequestProvider.get();

        try {
            Publisher pub =(Publisher)method.invoke(proxy,args);
            Spouts.from(pub).onEmptySwitch(()->Spouts.of(Response.noContent().build()))
                .forEach(1,context::resume, context::resume);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static class AsyncResourceMethodInvocationHandlerProvider implements ResourceMethodInvocationHandlerProvider{
        @Inject
        private InjectionManager injectionManager;

        @Override
        public InvocationHandler create(Invocable method) {
            Class returnType = method.getRawResponseType();
            if(Publisher.class.isAssignableFrom(returnType) & !Collection.class.isAssignableFrom(returnType)){
                return injectionManager.createAndInitialize(AsyncInvocationHandler.class);
            }
            return null;
        }
    }
}
