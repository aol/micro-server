
#Microserver

[![Join the chat at https://gitter.im/aol/micro-server](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/aol/micro-server?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

A convenient modular engine for Microservices. Microserver plugins offer seamless integration with Spring (core), Jersey, Guava, Grizzly, reactive programming, Hibernate & Spring Data, Codahale Metrics, Swagger and more to come!

## Quick start

Install Microserver with Grizzly and Jersey (Gradle config below)

    compile group: 'com.aol.microservices', name:'micro-grizzly-with-jersey', version:'0.62'
 
Create and run a simple app 
 
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


# Tutorial and overview

[Tutorial](https://github.com/aol/micro-server/wiki/Getting-started-:-Tutorial) 

[Tutoiral code](https://github.com/aol/micro-server/tree/master/micro-tutorial)

###Quick start youtube video
[![Getting started video](https://cloud.githubusercontent.com/assets/9964792/6361863/9991c50c-bc7e-11e4-8d28-746b0b87b1da.png)](https://www.youtube.com/watch?v=McXy9oGRpfA&feature=youtu.be)


Note the main launch class has been changed from MicroServerStartup to MicroserverApp

## Blurb

Microserver is a zero configuration, standards based, battle hardened library to run Java Rest Microservices via a standard Java main class. It has been used in production in AOL since July 2014.


## Get Microserver


![Build health](https://travis-ci.org/aol/micro-server.svg)

* micro-grizzly-with-jersey
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-grizzly-with-jersey/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-grizzly-with-jersey)
* micro-core 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-core)
* micro-boot 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-boot)


##Info

[wiki](https://github.com/aol/micro-server/wiki)

[Google Group](https://groups.google.com/forum/#!forum/micro-server)

[Example Apps : Microserver Core with Grizzly and Jersey](https://github.com/aol/micro-server/tree/master/micro-grizzly/src/test/java/app)

[Example Apps : Microserver Boot](https://github.com/aol/micro-server/tree/master/micro-boot/src/test/java/app)

[Java Doc : Microserver Core](http://www.javadoc.io/doc/com.aol.microservices/micro-core/0.62)

[Java Doc : Microserver Boot](http://www.javadoc.io/doc/com.aol.microservices/micro-core/0.62)



### Maven dependency

Microserver core 

    <dependency>
      <groupId>com.aol.microservices</groupId>
      <artifactId>micro-core</artifactId>
      <version>0.62</version>
    </dependency>
    
Microserver Spring Boot 

    <dependency>
      <groupId>com.aol.microservices</groupId>
      <artifactId>micro-boot</artifactId>
      <version>0.62</version>
    </dependency>



### Gradle dependency

Microserver core 
	
	 compile group: 'com.aol.microservices', name:'micro-core', version:'0.62'
	 
Microserver Spring Boot 
	 
	  compile group: 'com.aol.microservices', name:'micro-boot', version:'0.62'

##Tech Stack

Microserver core is a lightweight server configuration engine built using Spring, Cyclops and Jackson.



##Zero Configuration

No directory structure is imposed by the server and no XML is required. There is no framework config. Just a jar file and your application. You can of course, configure your application without limit.

Example working application :-

###The main class :-

     
     public class AppRunnerTest {

		
		public static void main(String[] args) throws InterruptedException {
			new MicroserverApp(() -> "test-app").run();
		}

    }


This will deploy a REST server on port 8080 (configurable by test-app.port in application.properties), it will also automagically capture any Rest end points (Spring & Jersey annotations) that implement the tag interface RestResource (see below for an example).

###A rest end point 


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

### Configuration Options

If you find you need configuration options for your application you have two options.

1. Override some of the available options on the Module interface (ConfigurableModule provides a builder mechanism for this)
2. [Implement a custom plugin](https://github.com/aol/micro-server/wiki/Creating-a-Microserver-plugin) (the cleanest option, which also promotes reuse across services).

### Application configuration (for Grizzly with Jersey)

The core of Microserver is a Spring 4.x Dependency Injection container which is used to store all the main classes of your Microservice (s). The Spring Dependency Injection container can be configured by the @Microservice Annotation on your main class, or by the Config object (optionally passed as a parameter to startup).

Each Microservice is a Jersey REST Application. Multiple Microservices can run on the same server, by adding them to the classpath at runtime. They share a common Spring Dependency Injection container (as they are smaller services, we feel it makes sense to share resources such as ThreadPools, Datasources etc), but act as totally separate Rest applications. 

When creating embedded Microservices (multiple services colocated on the same JVM and Spring container), development project should be independent, but the colocated instances should be tested as they will be depolyed in production. There will be more info to follow on the wiki, on how and why we have implemented and scaled this pattern (the goal is to achieve both the benefits of a full Microservice architecture, but minimise the costs as articulated by Robert (Uncle Bob) C. Martin and others - e.g. [here: Microservices and Jars](http://blog.cleancoder.com/uncle-bob/2014/09/19/MicroServicesAndJars.html) .

Jersey REST Applications are configured by the Module interface (at least one of which must be specified on startup).

![high level architecture](https://cloud.githubusercontent.com/assets/9964792/6375067/a6e4f65a-bd0c-11e4-85dc-82ae0d95d44b.png)


####Rest configuration

The configuration of your Rest end points can be managed via the Module interface. The Module interface has a number of Java 8 default methods and a single abstract method (getContext).  It behaves as a functional interface, and can be defined by a lambda expression. When used in this way the lambda represents the context the Microserver will create Rest end points on.

e.g. 


    new MicroserverApp(() -> "context").start();


() -> "context"  is a Module!


####Configurable Options

Module provides the following default methods, that clients can override

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

RestResource class defines the tag interface used to identify Rest end points for this module.

Filters provides a map of Servlet filters and the paths to which they should be applied

Providers allows client code to change the Jersey Providers packages

JaxWsRsApplication allows client code to completely override the Microserver jax.ws.rs.Application

####Property file configuration

Microserver supports auto-discovery of application.properties. Microserver will assume a default file name of 'application.properties'.  Microserver will check for a properties in the following order

1. System property 'application.property.file' and if present will load the property file from disk using that. 

2. Otherwise Microserver will look for a System Property 'application.env' and will load the application property file from the classpath using the resource name 'application-${application.env}.properties. 

3. Alternatively Microserver will load application.properties directly from the classpath.

4. If still not found Microserver will load application.properties from disk in the current directory

The default file name application.properties can be configured by exception (use PropertyFileConfig.setApplicationPropertyFileName(String filename).

Microserver application properties loading is configured by the class PropertyFileConfig. You can replace this with your own Spring configuration file to load property files by a different set of rules (by passing in your class to the constructor of Microserver).

##Embed and colocate Microservices

Microserver supports the embedding of multiple microservices within a single Microserver. All Microservices will share a single Spring context, so some care needs to be taken when authoring such Microservices to avoid conflicts. This does mean that they can share resources (such as database connections) where it makes sense to do so.

Embedded microservices should be collated at '''runtime only'''. There should be no compile time dependency between embedded microservices (otherwise you are not building microservices but a monolithc application).

Embedding microservices is an optimisation that allows better performance, enhanced robustness and reliability and easier management  of microservices - while still maintaining the advantages of horizontal scalability offered by the microservices approach.

###Embeded Microservices example

This example will start two different Rest end points - one on context "test-app" and another on context "alternative-app".
"test-app" will automagically wire in any Jersey end points that implement TestAppRestResource.
"alternative-app" will automagically wire in any Jersey end points that implement AltAppRestResource.

    @Microserver
    public class EmbeddedAppRunnerTest {

	public static void main(String[] args) throws InterruptedException {
		new MicroserverApp(EmbeddedAppRunnerTest.class, 
				new EmbeddedModule(TestAppRestResource.class,"test-app"),
				new EmbeddedModule(AltAppRestResource.class,"alternative-app")).start();

		

	}

	
  }




##Building a 'fat' Jar

We recommend the Gradle plugin Shadow Jar. For Gradle 2.0 simply define it in your plugins section ->

plugins {
  id 'java' // or 'groovy' Must be explicitly applied
  id 'com.github.johnrengelman.shadow' version '1.2.0'
}

Maven users can use Shade plugin or equivalent (Maven assembly plugin).

