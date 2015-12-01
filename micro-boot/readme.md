# Spring Boot entry point for Microserver

[micro-boot example apps](https://github.com/aol/micro-server/tree/master/micro-boot/src/test/java/app)

Use Microserver and Spring Boot together. 

## A simple example with one resource

* Annotate your classes with @Microboot to let Spring Boot know the base package for auto-scanning Spring beans.

* Create a MicroserverApp and run.

* You can now use the @Microserver annotation for configuration (except for base auto-scan packages)


```java
@Microboot //configure this package as the base for autoscan
//optionally use @Microserver here for more configuration options
public class SimpleExample {

	RestClient rest = new RestClient(10_000,1_000);
	
	
	public static void main (String[] args){
		
		new MicroserverApp(()-> "simple-app").start();
		
		assertThat(rest.get("http://localhost:8080/simple-app/status/ping"),equalTo("ok"));
		
	}
	

	
}
@Rest
@Path("/status")
public class SimpleResource{

	
	
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
		
		return "ok";
	}

	
}
```

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-boot)

Add micro-boot to the classpath

Maven

    <dependency>
       <groupId>com.aol.microservices</groupId>  
       <artifactId>micro-boot</artifactId>
       <version>x.yz</version>
    </dependency>

Gradle

    compile 'com.aol.microservices:micro-boot:x.yz'

And also add Grizzly and Jersey (micro-grizzly-with-jersey will add both)

Maven

    <dependency>
       <groupId>com.aol.microservices</groupId>
       <artifactId>micro-grizzly-with-jersey</artifactId>
       <version>x.yz</version>
    </dependency>

Gradle

    compile 'com.aol.microservices:micro-grizzly-with-jersey:x.yz'


## Create a simple server

    public class SimpleApp {

        public static void main(String[] args){
            new MicroserverApp(()->"test-app").run();
        }

    }

# Relationship to Microserver and Spring Boot

micro-boot allows you to use Microserver plugins & jax-rs support with Spring Boot back ends.


@Microboot is simply syntax sugar for 

 ```java
@Component
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
 ```

You can also define your own SpringBoot configuration if required.
