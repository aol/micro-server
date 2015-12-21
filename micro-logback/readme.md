# Logback plugin for Microserver

[Example micro-logback Apps](https://github.com/aol/micro-server/tree/master/micro-logback/src/test/java/com/aol/micro/server/logback/rest)

micro-logback plugin can be used in the following way:

Rest resources provided by this plugin can be used to manually control logging levels

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-logback/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-logback)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-logback</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-logback:x.yz'

# Configuration endpoints

## For Root Logger

* /logback/rootlogger/get/level
* /logback/rootlogger/change/to/all
* /logback/rootlogger/change/to/debug
* /logback/rootlogger/change/to/error
* /logback/rootlogger/change/to/fatal
* /logback/rootlogger/change/to/info
* /logback/rootlogger/change/to/of
* /logback/rootlogger/change/to/trace
* /logback/rootlogger/change/to/warn

## For a 'targetable' logger

* /logback/logger/get/level/{loggerName}   
* /logback/rootlogger/change/to/all/{loggerName}  
* /logback/rootlogger/change/to/debug/{loggerName}  
* /logback/rootlogger/change/to/error/{loggerName}  
* /logback/rootlogger/change/to/fatal/{loggerName}  
* /logback/rootlogger/change/to/info/{loggerName}  
* /logback/rootlogger/change/to/of/{loggerName}  
* /logback/rootlogger/change/to/trace/{loggerName}  
* /logback/rootlogger/change/to/warn/{loggerName} 

