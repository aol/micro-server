package app.rest.client.com.oath.micro.server;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.module.Module;
import com.oath.micro.server.spring.boot.MicroSpringBoot;

@Microserver
@MicroSpringBoot
public class Main {
    public static void main(String[] args) throws InterruptedException {
        new MicroserverApp( RestClientTest.class,  new Module() {
            @Override
            public String getContext() {
                return "rest-app";
            }
        });
        while(true){
            Thread.sleep(1000000l);
        }
    }
}
