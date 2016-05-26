package app.custom.binder.noanno;

import java.lang.reflect.InvocationHandler;

import org.glassfish.jersey.server.model.Invocable;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

public class CustomResourceInvocationHandlerProvider implements
        ResourceMethodInvocationHandlerProvider {

    @Override
    public InvocationHandler create(Invocable resourceMethod) {
            return new MyIncovationHandler();
    }

}
