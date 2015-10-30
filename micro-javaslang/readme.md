# Javaslang Plugin for Microserver

[micro-javaslang example apps](https://github.com/aol/micro-server/tree/master/micro-javslang/src/test/java/app)

This plugin 

1. Configures Jackson for Javaslang serialisation / deserialisation, so Javaslang types can be used as input and output to jax-rs Resources
2. Add cyclops-javaslang to your project which enables conversion between JDK / Javaslang / Guava / Jool / simple-react types and adds features such as for-comprehensions to Javaslang.

## To use

Simply add to the classpath

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-javaslang/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-javaslang)

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-javaslang</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-javaslang:x.yz'
    
