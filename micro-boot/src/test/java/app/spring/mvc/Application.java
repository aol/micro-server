package app.spring.mvc;
import java.util.concurrent.ExecutionException;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.boot.MicroBoot;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Microserver
@MicroBoot
public class Application {


    AsyncRestClient rest = new AsyncRestClient(1000,1000).withAccept("text/plain");

    @Test
    public void runAppAndBasicTest() throws InterruptedException, ExecutionException {

        new MicroserverApp( ()-> "spring-mvc");
        Thread.sleep(2000);

        assertThat(rest.get("http://localhost:8080/spring-mvc").get(),is("Greetings from Spring Boot with Microserver!"));

    }


}
