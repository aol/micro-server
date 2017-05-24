package com.aol.micro.server.slack;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.*;

import org.junit.*;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;


@Microserver(propertiesName = "application.properties")
public class SlackRunnerTest {

    private MicroserverApp server;

    @Before
    public void startServer() {
        server = new MicroserverApp(() -> "simple-app");
        server.start();
    }

    @After
    public void stopServer() {
        server.stop();
    }

    @Test
    public void simpleMessageTest(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setBasePath("/simple-app/slack/message")
                .setPort(8080)
                .setBody("Message from "+this.getClass().getName())
                .setContentType("application/json")
                .setAccept("text/plain")
                .build();
        
        assertEquals("OK",given().spec(requestSpec).when().post().andReturn().body().asString());
    }
}
