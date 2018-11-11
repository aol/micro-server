package app.groovy;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.auto.discovery.Rest;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.Module;
//import groovy.lang.Closure;
//import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Rest
@Path("/status")
@Microserver
public class StatusGroovy {
  public static void main(String[] args) {
/**
    List<Thread> app = new MicroserverApp(StatusGroovy.class, DefaultGroovyMethods.asType(new Closure<String>(null, null) {
      public String doCall() {
        return "status-app";
      }

    }, Module.class)).start();
 **/

  }

  @GET
  @Produces("text/plain")
  @Path("/ping")
  public String respondToPing() {
    return "pong!";
  }

}
