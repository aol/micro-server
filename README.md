
# Microserver

[![Build Status](https://travis-ci.org/aol/micro-server.svg)](https://travis-ci.org/aol/micro-server)
[![Join the chat at https://gitter.im/aol/micro-server](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/aol/micro-server?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A convenient modular engine for Microservices. Microserver plugins offer seamless integration with Spring (core), Jersey, Guava, Tomcat, Grizzly, reactive programming, Hibernate (& Spring Data), Spring Boot, Codahale Metrics, Swagger and more to come!

* [Microserver screencast : getting started with plugins](https://www.youtube.com/watch?v=sYn2cVTkfcM)

![screen shot 2016-05-06 at 12 30 26 pm](https://cloud.githubusercontent.com/assets/9964792/15588807/8da91440-2387-11e6-979b-f24d456541f5.png)

### Microserver plugins video
[![Getting started video](https://cloud.githubusercontent.com/assets/9964792/6361863/9991c50c-bc7e-11e4-8d28-746b0b87b1da.png)](https://youtu.be/sYn2cVTkfcM)



## Quick start

Install Microserver with Grizzly, Jackson and Jersey (Gradle config below)
```groovy
    compile group: 'com.oath.microservices', name:'micro-grizzly-with-jersey', version:'x.yz'
```   
Install Microserver with Tomcat, Jackson and Jersey (Gradle config below)
 ```groovy
    compile group: 'com.oath.microservices', name:'micro-tomcat-with-jersey', version:'x.yz'    
 ```
Create and run a simple app 
 ```java
    @Rest
	@Path("/test")
	public class SimpleApp {

		public static void main(String[] args){
			new MicroserverApp(()->"test-app").run();
		}
		@GET
		public String myEndPoint(){
			return "hello world!";
		}
    }
```

Browse to *http://localhost:8080/test-app/test*

See the response  *hello world!*

Add plugins by adding them to your build file - rerun the app to get new end points, Spring beans and more!

## Easy to use async NIO based REST

Return any reactive-streams Publisher from your REST end point to make them execute asynchronously automatically.

E.g. Using Future from [cyclops-react](cyclops-react.io)

```java
   @GET
   public Future<String> myEndPoint(){
          return Future.of(()->{
                                        sleep();
                                        return "hello world!";
                                        }, Executors.newFixedThreadPool(1));
   }
```

Would be equivalent to the following code

```java
 @GET
 public void myEndPoint(@Suspended AsyncResponse asyncResponse){
      Future.of(()->{
                                           sleep();
                                           asyncResponse.resume("hello world!");
                                           return 1;
		}, Executors.newFixedThreadPool(1));
}
```

# Why Microserver?

Microserver is a plugin engine for building Spring and Spring Boot based microservices. Microserver supports pure microservice and micro-monolith development styles. The micro-monolith style involves packaging multiple services into a single deployment - offering developers the productivity of microservice development without the operational risk. This can help teams adopt a Microservices architecture on projects that are currently monoliths.

Microserver plugins are orthogonal to Microservices. They solve a common problem in Microservice development whereby services are broken up and deployed separately but the code remains entangled in a monolithic common library. By making use of a plugin system that follows the same modular architectural principals as microservice development, teams can keep cross-service concerns and infrastructure in properly size, coherent and cohesive plugin modules.

# Tutorial and overview

[Tutorial](https://github.com/aol/micro-server/wiki/Getting-started-:-Tutorial) 

[Tutorial code](https://github.com/aol/micro-server/tree/master/micro-tutorial)

## Note on Fat Jars

Microserver (& Cyclops) have a plugin architecture and make use of the Java Service Loader mechanism. Make sure your Fat Jar implementation is configured to aggregate services. With the Gradle Shadow Jar you do this with
 ```groovy
    shadowJar {
      mergeServiceFiles()
    }
 ```


### Quick start youtube video

[![Getting started video](https://cloud.githubusercontent.com/assets/9964792/6361863/9991c50c-bc7e-11e4-8d28-746b0b87b1da.png)](https://www.youtube.com/watch?v=McXy9oGRpfA&feature=youtu.be)


Note the main launch class has been changed from MicroServerStartup to MicroserverApp

## Blurb

Microserver is a zero configuration, standards based, battle hardened library to run Java Rest Microservices via a standard Java main class. It has been used in production in AOL since July 2014.


## Get Microserver


![Build health](https://travis-ci.org/aol/micro-server.svg)

* micro-grizzly-with-jersey
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-grizzly-with-jersey/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-grizzly-with-jersey)
* micro-tomcat-with-jersey
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-tomcat-with-jersey/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-tomcat-with-jersey)
* micro-core 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-core)
* micro-boot  : Microserver driving Spring Boot
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-boot)
* micro-spring-boot  : Spring Boot driving Microserver
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-spring-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-spring-boot)


## Info

[wiki](https://github.com/aol/micro-server/wiki)

[Google Group](https://groups.google.com/forum/#!forum/micro-server)

[Example Apps : Microserver Core with Grizzly and Jersey](https://github.com/aol/micro-server/tree/master/micro-grizzly/src/test/java/app)

[Example Apps : Microserver Boot](https://github.com/aol/micro-server/tree/master/micro-boot/src/test/java/app)

[Java Doc : Microserver Core](http://www.javadoc.io/doc/com.oath.microservices/micro-core/0.62)

[Java Doc : Microserver Boot](http://www.javadoc.io/doc/com.oath.microservices/micro-core/0.62)



### Maven dependency

Microserver Grizzly with Jersey
 ```xml
    <dependency>
      <groupId>com.oath.microservices</groupId>
      <artifactId>micro-grizzly-with-jersey</artifactId>
      <version>x.yz</version>
    </dependency>
```
Microserver Spring Boot 
 ```xml
    <dependency>
      <groupId>com.oath.microservices</groupId>
      <artifactId>micro-spring-boot</artifactId>
      <version>x.yz</version>
    </dependency>
 ```


### Gradle dependency

Microserver core 
 ```groovy	
	 compile group: 'com.oath.microservices', name:'micro-core', version:'x.yz'
 ```	 
Microserver Spring Boot 
 ```groovy	 
	  compile group: 'com.oath.microservices', name:'micro-spring-boot', version:'x.yz'
 ```
## Tech Stack

Microserver core is a lightweight server configuration engine built using Spring, Cyclops and Jackson.



## Zero Configuration

No directory structure is imposed by the server and no XML is required. There is no framework config. Just a jar file and your application. You can, of course, configure your application without limit.

Example working application :-

### The main class :-

```java   
     public class AppRunnerTest {

		
		public static void main(String[] args) throws InterruptedException {
			new MicroserverApp(() -> "test-app").run();
		}

    }
 ```

This will deploy a REST server on port 8080 (configurable by test-app.port in application.properties), it will also automagically capture any Rest end points (Spring & Jersey annotations) that implement the tag interface RestResource (see below for an example).

### A rest end point

```java
@Rest
@Path("/status")
public class StatusResource {

    @GET
    @Produces("text/plain")
    @Path("/ping")
    public String ping() {
        return "ok";
    }

}
```
### Configuration Options

If you find you need configuration options for your application you have two options.

1. Override some of the available options on the Module interface (ConfigurableModule provides a builder mechanism for this)
2. [Implement a custom plugin](https://github.com/aol/micro-server/wiki/Creating-a-Microserver-plugin) (the cleanest option, which also promotes reuse across services).

### Application configuration (for Grizzly with Jersey)

The core of Microserver is a Spring 4.x Dependency Injection container which is used to store all the main classes of your Microservice (s). The Spring Dependency Injection container can be configured by the @Microservice Annotation on your main class, or by the Config object (optionally passed as a parameter to startup).

### Micro-monolith Architectural Overview

Each Microservice is a Jersey REST Application, these can be deployed independently as pure Microservices or together as a micro-monolith. Multiple Microservices can run on the same server, by adding them to the classpath at runtime. They share a common Spring Dependency Injection container (as they are smaller services, we feel it makes sense to share resources such as ThreadPools, Datasources etc), but act as totally separate Rest applications. 

When creating embedded Microservices (multiple services colocated on the same JVM and Spring container), the development project should be independent, but the colocated instances should be tested as they will be deployed in production. There will be more info to follow on the wiki, on how and why we have implemented and scaled this pattern (the goal is to achieve both the benefits of a full Microservice architecture, but minimise the costs as articulated by Robert (Uncle Bob) C. Martin and others - e.g. [here: Microservices and Jars](http://blog.cleancoder.com/uncle-bob/2014/09/19/MicroServicesAndJars.html) .

Jersey REST Applications are configured by the Module interface (at least one of which must be specified on startup).

![high level architecture](https://cloud.githubusercontent.com/assets/9964792/6375067/a6e4f65a-bd0c-11e4-85dc-82ae0d95d44b.png)


#### Rest configuration

The configuration of your Rest endpoints can be managed via the Module interface. The Module interface has a number of Java 8 default methods and a single abstract method (getContext).  It behaves as a functional interface, and can be defined by a lambda expression. When used in this way the lambda represents the context the Microserver will create Rest end points on.

e.g. 

 ```java
    new MicroserverApp(() -> "context").start();
 ```

() -> "context"  is a Module!


#### Configurable Options

Module provides the following default methods, that clients can override

 ```java
    default Map<String,String> getPropertyOverrides(){
		return Maps.newHashMap();
	}
	default Set<Class> getSpringConfigurationClasses(){
		return Sets.newHashSet(Classes.CORE_CLASSES.getClasses());
	}
	default List<Class> getRestResourceClasses() {
		return Arrays.asList(RestResource.class);
	}
	default List<Class> getRestAnnotationClasses() {
		return Arrays.asList(Rest.class);
	}
	
	default List<String> getDefaultJaxRsPackages(){
		return Arrays.asList("com.wordnik.swagger.sample.resource",
				"com.wordnik.swagger.sample.util"	);
	}
	
	default List<Class> getDefaultResources(){
		return Arrays.asList(JacksonFeature.class, 
				//SWAGGER CLASSES
				ApiListingResourceJSON.class,JerseyApiDeclarationProvider.class,
				JerseyResourceListingProvider.class);
	}
	
	default List<ServletContextListener> getListeners(ServerData data){
		return ImmutableList.of(new ContextLoaderListener(data
				.getRootContext()),
				new JerseySpringIntegrationContextListener(data),
				new SwaggerInitializer(data));
	}
	default Map<String,Filter> getFilters(ServerData data) {
		return ImmutableMap.of("/*",new QueryIPRetriever());
	}
	default Map<String,Servlet> getServlets(ServerData data) {
		return ImmutableMap.of();
	}
	
	default  String getJaxWsRsApplication(){
		return JerseyRestApplication.class.getCanonicalName();
	}
	default String getProviders(){
		return "com.aol.micro.server.rest.providers";
	}
 ```
RestResource class defines the tag interface used to identify Rest end points for this module.

Filters provides a map of Servlet filters and the paths to which they should be applied

Providers allows client code to change the Jersey Providers packages

JaxWsRsApplication allows client code to completely override the Microserver jax.ws.rs.Application

#### Property file configuration

Microserver supports auto-discovery of application.properties. Microserver will assume a default file name of 'application.properties'.  Microserver will check for a properties in the following order

1. System property 'application.property.file' and if present will load the property file from disk using that. 

2. Otherwise, Microserver will look for a System Property 'application.env' and will load the application property file from the classpath using the resource name 'application-${application.env}.properties. 

3. Alternatively, Microserver will load application.properties directly from the classpath.

4. If still not found Microserver will load application.properties from disk in the current directory

The default file name application.properties can be configured by exception (use PropertyFileConfig.setApplicationPropertyFileName(String filename).

Microserver application properties loading is configured by the class PropertyFileConfig. You can replace this with your own Spring configuration file to load property files by a different set of rules (by passing in your class to the constructor of Microserver).

## Embed and colocate Microservices

Microserver supports the embedding of multiple microservices within a single Microserver, this is not the default mode of operation and involves a little more work to setup. All Microservices will share a single Spring context, so some care needs to be taken when authoring such Microservices to avoid conflicts. This does mean that they can share resources (such as database connections) where it makes sense to do so.

Embedded microservices should be collated at '''runtime only'''. There should be no compile time dependency between embedded microservices (otherwise you are not building microservices but a monolithic application).

Embedding microservices is an optimisation that allows better performance, enhanced robustness and reliability and easier management  of microservices - while still maintaining the advantages of horizontal scalability offered by the microservices approach.

### Embedded Microservices example

This example will start two different Rest endpoints - one on context "test-app" and another on context "alternative-app".
"test-app" will automagically wire in any Jersey endpoints that implement TestAppRestResource.
"alternative-app" will automagically wire in any Jersey endpoints that implement AltAppRestResource.
 ```java
	@Microserver
	public class EmbeddedAppRunnerTest {
	
		public static void main(String[] args) throws InterruptedException {
			new MicroserverApp(EmbeddedAppRunnerTest.class, 
					new EmbeddedModule(TestAppRestResource.class,"test-app"),
					new EmbeddedModule(AltAppRestResource.class,"alternative-app")).start();
		
			
		
		}
	}

 ```


## Building a 'fat' Jar

We recommend the Gradle plugin Shadow Jar. For Gradle 2.0 simply define it in your plugins section ->
 ```groovy
plugins {
  id 'java' // or 'groovy' Must be explicitly applied
  id 'com.github.johnrengelman.shadow' version '1.2.0'
}
 ```
Maven users can use Shade plugin or equivalent (Maven assembly plugin).
 
# Thanks to our Sponsors

* ![YourKit Logo](https://www.yourkit.com/images/yklogo.png) YourKit supports open source projects with innovative and intelligent tools
for monitoring and profiling Java and .NET applications.
YourKit is the creator of <a href="https://www.yourkit.com/java/profiler/">YourKit Java Profiler</a>,
<a href="https://www.yourkit.com/.net/profiler/">YourKit .NET Profiler</a>,
and <a href="https://www.yourkit.com/youmonitor/">YourKit YouMonitor</a>.
