package app.rest.client.com.oath.micro.server;

import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LoggingExMapper implements ExceptionMapper<Throwable> {
    public LoggingExMapper(){
        System.out.println("registered");
    }
    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();
        return Response.status(400).build();
    }
}
