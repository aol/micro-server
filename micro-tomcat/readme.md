# Tomcat web server Plugin

[Example Tomcat apps](https://github.com/aol/micro-server/tree/master/micro-tomcat/src/test/java/app)

Plugin that allows the Tomcat Web server to be used with Microserver. (Since v0.79 of Microserver)

## Note

Tomcat does not currently support the Microserver micro-monolith style of development where by multiple Microservers can be rolled up into a single 'monolithic' style services at runtime. If you require that type of functionality take a look at micro-grizzly instead.


## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-grizzly/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-tomcat)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-tomcat</artifactId>
        <version>x.yz</version>
     </dependency>
 ```    
Gradle
```groovy
    compile 'com.oath.microservices:micro-tomcaty:x.yz'
```