package com.oath.micro.server.rest.jersey;

import cyclops.reactive.Spouts;
import cyclops.reactive.collections.mutable.ListX;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.aopalliance.intercept.ConstructorInterceptor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InterceptionService;
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
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class AsyncInterceptionService implements InterceptionService {

    @Override
    public Filter getDescriptorFilter() {
        return new Filter() {
            @Override
            public boolean matches(Descriptor d) {
                return d.getImplementation().startsWith("app.publisher.binder.direct");

            }
        };
    }

    @Override
    public List<MethodInterceptor> getMethodInterceptors(Method method) {
        return ListX.of(new AsyncDispatcherMethodInterceptor(asyncContext,serviceLocator));
    }

    @Override
    public List<ConstructorInterceptor> getConstructorInterceptors(Constructor<?> constructor) {
        return new ArrayList<>();
    }



    @Context
    private javax.inject.Provider<AsyncContext> asyncContext;
    @Context
    private javax.inject.Provider<ContainerRequestContext> containerRequestContext;
    @Context
    private ServiceLocator serviceLocator;


    @AllArgsConstructor
    @NoArgsConstructor
    static class AsyncDispatcherMethodInterceptor implements MethodInterceptor {
        javax.inject.Provider<AsyncContext> asyncContext;
        private ServiceLocator serviceLocator;

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            Method method = invocation.getMethod();
            Class returnType = method.getReturnType();
            if (Publisher.class.isAssignableFrom(returnType) & !Collection.class.isAssignableFrom(returnType)) {
                final AsyncContext context = this.asyncContext.get();
                if (!context.suspend())
                    throw new ProcessingException(LocalizationMessages.ERROR_SUSPENDING_ASYNC_REQUEST());


                Publisher pub = (Publisher) invocation.proceed();
                Spouts.from(pub).onEmptySwitch(() -> Spouts.of(Response.noContent().build()))
                    .forEach(1, context::resume, context::resume);

                return null;

            }
            return invocation.proceed();
        }



    }



}
