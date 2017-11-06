# Logback plugin for Microserver

[Example micro-logback Apps](https://github.com/aol/micro-server/tree/master/micro-log4j/src/test/java/app)

micro-logback plugin can be used in two ways:

1. Rest resources provided by this plugin can be used to manually control logging levels
2. LogbackRootLoggerChecker can be used to automatically bring logging level to a specified one for rootLogger


## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-logback/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-logback)

Simply add to the classpath

Maven 

```xml
     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-logback</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.oath.microservices:micro-logback:x.yz'
```

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

# Configuring LogbackRootLoggerChecker

This is a scheduled job that periodically enforces a specified log level. For example, you can be sure that any attempt at changing the log level to DEBUG or TRACE via a Rest call is purely temporary and will be reset to INFO by the checker.

LogbackRootLoggerChecker can be configured by setting the following properties in application.properties, instance.properties or via the Microserver annotation

     logback.root.logger.checker.active: (e.g. true | false)
     logback.root.logger.checker.correct.level: (e.g. INFO)
     logback.root.logger.checker.fixed.rate: (e.g. 5000)
     
Users can also use LogbackRootLoggerResource to change either logback.root.logger.checker.active or logback.root.logger.checker.correct.level at runtime

## Checker endpoints


* /logback/rootlogger/checker/is/{active}  = true | false
* /logback/rootlogger/checker/level/{correctLevelStr} = all | debug |  error | info | warn | fatal | trace
