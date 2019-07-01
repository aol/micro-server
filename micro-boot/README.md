# Spring Boot entry point for Microserver

[micro-boot example apps](https://github.com/aol/micro-server/tree/master/micro-boot/src/test/java/app)

**micro-boot** allows Microserver front ends to use Microserver plugins with Spring Boot without configuring support for the micro-jersey plugin. Rest and Web end points in Microserver
plugins may not be available (but the Spring beans will be and can be used to expose the same data in a different manner). 
To use full-stack Spring Boot with Microserver (and. Jersey) see the [micro-spring-boot plugin](https://github.com/aol/micro-server/tree/master/micro-spring-boot).
## A simple example with one resource

* Annotate your classes with @Microboot to let Spring Boot know the base package for auto-scanning Spring beans.

* Create a MicroserverApp and run.

* You can now use the @Microserver annotation for configuration (except for base auto-scan packages)

[Spring Boot Hello World example](https://spring.io/guides/gs/spring-boot/) converted to a micro-boot test

```java
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


@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot with Microserver!";
    }

}

```

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-boot)

Add micro-boot to the classpath

Maven

    <dependency>
       <groupId>com.oath.microservices</groupId>  
       <artifactId>micro-boot</artifactId>
       <version>x.yz</version>
    </dependency>

Gradle

    compile 'com.oath.microservices:micro-boot:x.yz'

And also add Grizzly and Jersey (micro-grizzly-with-jersey will add both)

Maven

    <dependency>
       <groupId>com.oath.microservices</groupId>
       <artifactId>micro-grizzly-with-jersey</artifactId>
       <version>x.yz</version>
    </dependency>

Gradle

    compile 'com.oath.microservices:micro-grizzly-with-jersey:x.yz'


## Create a simple server
```java
@Microboot
@Path("/simple")
public class SimpleApp {

        public static void main(String[] args){
            new MicroserverApp(()->"test-app").run();
        }
        
        @GET
        public String ping() {
            return "ok";
        }
}
```
# Relationship to Microserver and Spring Boot

micro-boot allows you to use Microserver plugins & jax-rs support with Spring Boot back ends.


@Microboot is simply syntax sugar for 

 ```java
@Component
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
 ```

You can also define your own SpringBoot configuration if required.
