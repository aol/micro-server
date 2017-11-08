# Spring Boot entry point for Microserver

[micro-spring-boot example apps](https://github.com/aol/micro-server/tree/master/micro-spring-boot/src/test/java/app)

Micro-spring-boot allows Microserver Jersey annotations and Microserver plugins to be used on applications where the full-stack is managed by Spring Boot. To use Microserver front-ends and Spring Boot backends see the micro-boot plugin.


## A simple example with one resource

* Annotate your classes with @Microboot to let Spring Boot know the base package for auto-scanning Spring beans.

* Create a MicroserverApp and run.

* You can now use the @Microserver annotation for configuration (except for base auto-scan packages)


```java

@MicroSpringBoot //configure this package as the base for autoscan
//optionally use @Microserver here for more configuration options
public class SimpleExample {

	RestClient rest = new RestClient(10_000,1_000);
	
	
	public static void main (String[] args){
		
		new MicroserverApp(()-> "simple-app"); //note unlike traditional microserver apps, there is no need to call start or run here
		
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

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-boot)

Add micro-spring-boot to the classpath

Maven

    <dependency>
       <groupId>com.oath.microservices</groupId>  
       <artifactId>micro-spring-boot</artifactId>
       <version>x.yz</version>
    </dependency>

Gradle

    compile 'com.oath.microservices:micro-spring-boot:x.yz'



## Create a simple server

```java

@MicroSpringBoot
@Path("/simple")
public class SimpleApp {

        public static void main(String[] args){
            new MicroserverApp(()->"test-app");
        }
        
        @GET
        public String ping() {
            return "ok";
        }
}

```


@MicroSpringBoot is simply syntax sugar for 

 ```java
 
@Component
@SpringBootApplication(exclude = SpringDataWebAutoConfiguration.class)
 ```

You can also define your own SpringBoot configuration if required.

## A note on overriding Module properties

1. micro-spring-boot currently only uses the context provided in the Module passed to MicroserverApp, all other properties aren't used at that stage.
2. To pass a Module with overrides into micro-spring-boot, you need to make the Module a Spring Bean. The easiest way to do this is have your main class implement Module and override getContext(), then pass an instance to MicroserverApp
3. See https://github.com/aol/micro-server/blob/master/micro-spring-boot/src/test/java/app/swagger/com/aol/micro/server/SwaggerRunnerTest.java for an example
