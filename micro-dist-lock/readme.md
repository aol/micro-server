# Distributed lock plugin

This plugin provides some common code for distributed lock. Clients use it only need to provide key/keys. If multiple keys are provided, the concatenated key will be used.

This plugin needs to be used in conjunction with a concrete distributed lock plugin i.e. micro-mysql plugin.

This plugin also provides rest end points which can be used to check if a process is main process.

## Getting The Microserver Distributed lock Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-dist-lock/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-dist-lock)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-dist-lock</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle
```groovy
    compile 'com.aol.microservices:micro-dist-lock:x.yz'
```