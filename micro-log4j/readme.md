# Log4j plugin for Microserver

[Example micro-log4j Apps](https://github.com/aol/micro-server/tree/master/micro-log4j/src/test/java/app)

micro-log4j plugin can be used in two ways:

1. Rest resources provided by this plugin can be used to manually control logging levels
2. Log4jRootLoggerChecker can be used to automatically bring logging level to a specified one for rootLogger

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-log4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-log4j)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-log4j</artifactId>
        <version>x.yz</version>
     </dependency>
```     
Gradle
```groovy
    compile 'com.oath.microservices:micro-log4j:x.yz'
```

# Configuration endpoints

## For Root Logger

* /log4j/rootlogger/get/level
* /log4j/rootlogger/change/to/all
* /log4j/rootlogger/change/to/debug
* /log4j/rootlogger/change/to/error
* /log4j/rootlogger/change/to/fatal
* /log4j/rootlogger/change/to/info
* /log4j/rootlogger/change/to/of
* /log4j/rootlogger/change/to/trace
* /log4j/rootlogger/change/to/warn

## For a 'targetable' logger

* /log4j/logger/get/level/{loggerName}   
* /log4j/rootlogger/change/to/all/{loggerName}  
* /log4j/rootlogger/change/to/debug/{loggerName}  
* /log4j/rootlogger/change/to/error/{loggerName}  
* /log4j/rootlogger/change/to/fatal/{loggerName}  
* /log4j/rootlogger/change/to/info/{loggerName}  
* /log4j/rootlogger/change/to/of/{loggerName}  
* /log4j/rootlogger/change/to/trace/{loggerName}  
* /log4j/rootlogger/change/to/warn/{loggerName}  

# Configuring Log4jRootLoggerChecker

This is a scheduled job that periodically enforces a specified log level. For example, you can be sure that any attempt at changing the log level to DEBUG or TRACE via a Rest call is purely temporary and will be reset to INFO by the checker.

Log4jRootLoggerChecker can be configured by setting the following properties in application.properties, instance.properties or via the Microserver annotation

     log4j.root.logger.checker.active: (e.g. true | false)
     log4j.root.logger.checker.correct.level: (e.g. INFO)
     log4j.root.logger.checker.fixed.rate: (e.g. 5000)
     
Users can also use Log4jRootLoggerResource to change either log4j.root.logger.checker.active or log4j.root.logger.checker.correct.level at runtime

## Checker endpoints


* /log4j/rootlogger/checker/is/{active}  = true | false
* /log4j/rootlogger/checker/level/{correctLevelStr} = all | debug |  error | info | warn | fatal | trace