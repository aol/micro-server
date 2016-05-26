package app.custom.binder.test;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

import com.aol.micro.server.auto.discovery.JaxRsResource;

@JaxRsResource
public class CustomBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // this is where the magic happens!
        bind(CustomResourceInvocationHandlerProvider.class).to(
                ResourceMethodInvocationHandlerProvider.class);
    }
}