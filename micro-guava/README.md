# Guava Plugin for Microserver

[micro-guava example apps](https://github.com/aol/micro-server/tree/master/micro-guava/src/test/java/app)

This plugin 

1. Configures Jackson for Guava serialisation / deserialisation, so Guava types can be used as input and output to jax-rs Resources
2. Configures a Guava EventBus as a Spring Bean (named microserverEventBus)  

## To use

Simply add to the classpath

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-guava/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-guava)

Maven 

     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-guava</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.oath.microservices:micro-guava:x.yz'
    

# Example Guava Resource

    @Rest
    @Path("/status")
    public class GuavaAppResource  {

		@POST
		@Produces("application/json")
		@Path("/ping")
		
		public ImmutableList<String> ping( ImmutableGuavaEntity entity) {
			return entity.getList();
		}
		@POST
		@Produces("application/json")
		@Path("/optional")
		public Optional<String> optional(Jdk8Entity entity) {
			return entity.getName();
		}

   }
