
#Microserver

Choosing between Java EE or Spring? 

Microserver is the best of both! Spring, Jersey and Grizzly working together harmoniously for your Microservices.

##Getting started

[Tutorial](https://github.com/aol/micro-server/wiki/Getting-started-:-Tutorial) 

[Tutoiral code](https://github.com/aol/micro-server/tree/master/micro-tutorial)

###Quick start youtube video
[![Getting started video](https://cloud.githubusercontent.com/assets/9964792/6361863/9991c50c-bc7e-11e4-8d28-746b0b87b1da.png)](https://www.youtube.com/watch?v=McXy9oGRpfA&feature=youtu.be)


Note the main launch class has been changed from MicroServerStartup to MicroserverApp

## Blurb

Microserver is a zero configuration, standards based, battle hardened library to run Java Rest Microservices via a standard Java main class. It has been used in production in AOL since July 2014.


## Get Microserver


![Build health](https://travis-ci.org/aol/micro-server.svg)

* micro-core 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/microserver-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/microserver-core)
* micro-boot 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/microserver-boot/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/microserver-boot)


##Info

[wiki](https://github.com/aol/micro-server/wiki)

[Google Group](https://groups.google.com/forum/#!forum/micro-server)

[Example Apps : Microserver Core](https://github.com/aol/micro-server/tree/master/micro-core/src/test/java/app)

[Example Apps : Microserver Boot](https://github.com/aol/micro-server/tree/master/micro-boot/src/test/java/app)

[Java Doc : Microserver Core](http://www.javadoc.io/doc/com.aol.microservices/microserver-core/0.55)

[Java Doc : Microserver Boot](http://www.javadoc.io/doc/com.aol.microservices/microserver-core/0.55)



### Maven dependency

Microserver core 

    <dependency>
      <groupId>com.aol.microservices</groupId>
      <artifactId>microserver-core</artifactId>
      <version>0.55</version>
    </dependency>
    
Microserver Spring Boot 

    <dependency>
      <groupId>com.aol.microservices</groupId>
      <artifactId>microserver-boot</artifactId>
      <version>0.55</version>
    </dependency>



### Gradle dependency

Microserver core 
	
	 compile group: 'com.aol.microservices', name:'microserver-core', version:'0.55'
	 
Microserver Spring Boot 
	 
	  compile group: 'com.aol.microservices', name:'microserver-boot', version:'0.55'

##Tech Stack

Microserver seamlessly integrates Jersey 2, Spring 4, Guava, Codahale Metrics, Swagger, Spring Data / JDBC / Hibernate, SimpleReact and Grizzly into a standalone REST server that can be leveraged as a simple library within a Java app.

It also includes features, and supports patterns we have found useful in scaling out our own Microservices infrastructure.

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

###Application configuration

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

#Release notes

v0.1 : Initial release
Grizzly NIO Java EE Server
Jersey for REST
Spring 4
Guava for Immutable Collections
Jackson configured for Guava and Java 8
SimpleReact
Filters and Servlets
IP Capturing Filter
Embedded  Microservices

v0.2 :
1. Full Swagger integration
2. Spring NIO Rest clients that return completablefutures
3. Autodiscovery and declartive config for ServletListeners
4. Sample App showing how take full advantage of Grizzly's NIO server capabilities from within Jersey (using AsyncResponse - take a look at AsyncResource if interested).

v0.3 :
1. @Microservice configuring annotation
2. JaxRS NIO Rest Client
3. Codahale Metrics

v0.4
Simpler & more powerful configuration (annotation, Configuration class and module level).
Relational db integration (Datasources, hibernate, JDBC, Generic DAOs (Hibernate / JDBC / Spring Data).
Events and Scheduled job monitoring and REST end points

Examples :

Startup

@Microserver annotation on class and then

new Microserver( ()-> "context");

is all you need. (Will automatically identify calling class and search for annotations)


.Datasource configuration
Configure multiple datasources
Hibernate entity manager
Spring transaction manager automatically

Artefacts automatically configured for *main* data source

.Jdbc
Spring JDBC template bean available
ROMA - object mapping for Spring JDBC integrated
Spring data JDBCRepo integrated



.Hibernate integration
GenericHibernateDAO created per data source 
GenericService (Transactional created) (main only)

.Spring Data Integration
Spring data 'automagic' repos configured

. Events
Capture active and recently completed requests
. Running jobs
Capture active and recently completed scheduled jobs
Rest calls to view recent events & recent jobs

Microserver annotation configuration

    public @interface Microserver {

    
       String[] basePackages() default {};
       Class[] classes() default {};
       Classes[] springClasses() default {};
       String propertiesName() default "application.properties";
       String[] entityScan() default {};
       String[] properties() default {};
    }
   
v0.45

Rest end points can use the annotation @Rest instead of both @Component (Spring) and implementing RestResource. Users can specify their own tag annotations also.

ROMA - Spring Row Mapper has been made an optional dependency. It's only available on a minor Repo no-one really uses, so it caused problems building Microserver apps externally.

v0.5

Spring Boot support is added with v0.5, so now you can launch a Spring Boot application that runs on a Grizzly Server with Jersey 2 and Microserver Jersey / Spring Integration. Your Spring Boot apps can also take advantage of Microserver optimisations and enhancements such as co-locating services with shared Spring resources (you might want to do this, to reduce consumption of scarce resources such as datasource connections to critical dbs).

This version also includes validation via Jersey bean validation and hibernate validator.
