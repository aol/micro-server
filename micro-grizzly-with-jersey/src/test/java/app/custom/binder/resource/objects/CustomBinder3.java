package app.custom.binder.resource.objects;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

import com.aol.micro.server.auto.discovery.JaxRsResource;

public class CustomBinder3 extends AbstractBinder {

    @Override
    protected void configure() {
        // this is where the magic happens!
        bind(CustomResourceInvocationHandlerProvider.class).to(
                ResourceMethodInvocationHandlerProvider.class);
    }
}