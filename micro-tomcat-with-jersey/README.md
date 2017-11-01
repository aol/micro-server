# Tomcat, Jersey and Microserver together

[Example Tomcat & Jersey Apps](https://github.com/aol/micro-server/tree/master/micro-tomcat/src/test/java/app)

Convenience module that packages Tomcat, Jersey, Jackson and Microserver together

## Note

Tomcat does not currently support the Microserver micro-monolith style of development where by multiple Microservers can be rolled up into a single 'monolithic' style services at runtime. If you require that type of functionality take a look at micro-grizzly instead.


## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-grizzly-with-jersey/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-grizzly-with-jersey)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-tomcat-with-jersey</artifactId>
        <version>x.yz</version>
     </dependency>
```    
Gradle
```groovy
    compile 'com.aol.microservices:micro-tomcat-with-jersey:x.yz'
```