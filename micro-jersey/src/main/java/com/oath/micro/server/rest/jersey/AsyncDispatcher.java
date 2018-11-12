package com.oath.micro.server.rest.jersey;

import cyclops.reactive.Spouts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.AsyncContext;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.inject.ConfiguredValidator;


import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodDispatcher;
import org.reactivestreams.Publisher;


import javax.ws.rs.ProcessingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationHandler;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class AsyncDispatcher implements ResourceMethodDispatcher {

    private final ResourceMethodDispatcher originalDispatcher;

    @Context
    private javax.inject.Provider<AsyncContext> asyncContext;
    @Context
    private javax.inject.Provider<ContainerRequestContext> containerRequestContext;


    public AsyncDispatcher(ResourceMethodDispatcher originalDispatcher) {
        this.originalDispatcher = originalDispatcher;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    static class AsyncDispatcherProvider implements Provider{
        @Context
        private ServiceLocator serviceLocator;
       @Override
       public ResourceMethodDispatcher create(Invocable method, InvocationHandler handler, ConfiguredValidator validator) {
           final Class<?> returnType = method.getHandlingMethod().getReturnType();
           if(Publisher.class.isAssignableFrom(returnType) & !Collection.class.isAssignableFrom(returnType)){
              Set<Provider> providers = serviceLocator.getAllServiceHandles(ResourceMethodDispatcher.Provider.class)
                                                      .stream()
                                                      .filter(h->!h.getActiveDescriptor()
                                                              .getImplementationClass()
                                                              .equals(AsyncDispatcherProvider.class))
                                                        .map(ServiceHandle::getService)
                                                        .collect(Collectors.toSet());

               for (ResourceMethodDispatcher.Provider provider : providers) {
                   ResourceMethodDispatcher dispatcher = provider.create(method, handler, validator);
                   if (dispatcher != null) {
                       AsyncDispatcher result = new AsyncDispatcher(dispatcher);
                       serviceLocator.inject(result);
                       return result;
                   }
               }

           }
           return null;
       }
    }
    @Override
    public Response dispatch(Object resource, ContainerRequest request) throws ProcessingException {
        final AsyncContext context = this.asyncContext.get();
        if(!context.suspend())
            throw new ProcessingException(LocalizationMessages.ERROR_SUSPENDING_ASYNC_REQUEST());
        final ContainerRequestContext requestContext = containerRequestContext.get();

        Publisher pub = (Publisher)originalDispatcher.dispatch(resource, request)
                          .getEntity();
        Spouts.from(pub).onEmptySwitch(()->Spouts.of(Response.noContent().build()))
                    .forEach(1,context::resume, context::resume);

        return null;
    }


}
