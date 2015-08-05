# Spring Boot entry point for Microserver

[micro-boot example apps](https://github.com/aol/micro-server/tree/master/micro-boot/src/test/java/app)

## To use

Add micro-boot to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-boot</artifactId>
        <version>0.62</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-boot:0.62'
    
 And also add Grizzly and Jersey (micro-grizzly-with-jersey will add both)
 
 Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-grizzly-with-jersey</artifactId>
        <version>0.62</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-grizzly-with-jersey:0.62'
    
 
 ## Create a simple server
 
     public class SimpleApp {

	   public static void main(String[] args){
		   new MicrobootApp(()->"test-app").run();
	   }
	
     }
 
 # Relationship to Microserver and Spring Boot
 
 micro-boot allows you to use Microserver  plugins & jax-rs support with Spring Boot back ends.
 
 micro-boot apps are started via the MicrobootApp (equivalent to MicroserverApp) and configured via the Microboot annotation (equivalent to the Microserver annotation)