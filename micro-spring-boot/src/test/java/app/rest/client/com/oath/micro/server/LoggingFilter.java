package app.rest.client.com.oath.micro.server;

import com.oath.micro.server.auto.discovery.FilterConfiguration;
import cyclops.control.Either;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;

import static cyclops.control.Either.right;

@Component
public class LoggingFilter implements FilterConfiguration {
    @Override
    public String[] getMapping() {
        return new String[]{"/*"};
    }

    @Override
    public Either<Class<? extends Filter>, Filter> getFilter() {
        return right(new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                try {
                    chain.doFilter(request, response);
                }catch(Throwable e){
                    e.printStackTrace();
                }
                System.out.println("hello");
            }
        });
    }
}
