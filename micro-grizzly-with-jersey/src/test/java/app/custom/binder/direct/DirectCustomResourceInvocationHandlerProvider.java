package app.custom.binder.direct;

import java.lang.reflect.InvocationHandler;

import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

public class DirectCustomResourceInvocationHandlerProvider implements
        ResourceMethodInvocationHandlerProvider {

    @Override
    public InvocationHandler create(Invocable resourceMethod) {
            return new DirectMyIncovationHandler();
    }

}
