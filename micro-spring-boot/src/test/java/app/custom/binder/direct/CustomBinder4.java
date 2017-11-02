package app.custom.binder.direct;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.spi.internal.ResourceMethodInvocationHandlerProvider;

public class CustomBinder4 extends AbstractBinder {

    @Override
    protected void configure() {
        // this is where the magic happens!
        bind(DirectCustomResourceInvocationHandlerProvider.class).to(
                ResourceMethodInvocationHandlerProvider.class);
    }
}