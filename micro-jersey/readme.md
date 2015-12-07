# Jersey jax-rs Plugin

[Example Jersey Apps](https://github.com/aol/micro-server/tree/master/micro-grizzly/src/test/java/app)

Plugin that allows the Jersey to be used as the jax-rs implementation with Microserver. (micro-jersey does not include a webserver (such as micro-grizzly, or JSON serializer / deserializer such as micro-jackson-configuration. See micro-grizzly-with-jersey for an end-to-end solution).

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jersey/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jersey)

Simply add to the classpath

Maven 
 ```xml

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-jersey</artifactId>
        <version>x.yz</version>
     </dependency>

```    
Gradle
```groovy
    compile 'com.aol.microservices:micro-jersey:x.yz'
```