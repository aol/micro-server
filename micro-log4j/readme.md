# Log4j plugin for Microserver

[Example micro-log4j Apps](https://github.com/aol/micro-server/tree/master/micro-log4j/src/test/java/com/aol/micro/server/log4j)

micro-log4j plugin can be used in two ways:

1. Rest resources provided by this plugin can be used to manually control logging levels
2. Log4jRootLoggerChecker can be used to automatically bring logging level to a specified one for rootLogger

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-log4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-log4j)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-log4j</artifactId>
        <version>x.yz</version>
     </dependency>
```     
Gradle
```groovy
    compile 'com.aol.microservices:micro-log4j:x.yz'
```
# Configuring Log4jRootLoggerChecker

Log4jRootLoggerChecker can be configured by setting the following properties in application.properties, instance.properties or via the Microserver annotation

     log4j.root.logger.checker.active: (e.g. true | false)
     log4j.root.logger.checker.correct.level: (e.g. INFO)
     log4j.root.logger.checker.fixed.rate: (e.g. 5000)
     
Users can also use Log4jRootLoggerResource to change either log4j.root.logger.checker.active or log4j.root.logger.checker.correct.level at runtime

