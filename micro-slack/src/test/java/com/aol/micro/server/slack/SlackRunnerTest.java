package com.aol.micro.server.slack;

import static com.jayway.restassured.RestAssured.given;

import org.junit.*;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;


@Microserver
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
        given().when()
               .get("http://localhost:8080/simple-app/slack/message?txt=Message From "+this.getClass().getName())
               .then()
               .statusCode(200);
    }
}
