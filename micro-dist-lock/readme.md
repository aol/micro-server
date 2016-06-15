# Distributed lock plugin

This plugin provides some common code for distributed lock. Clients use it only need to provide lockName and key i.e. implements the LockKeyProvider interface and then autowire the LockController.
The idea here is that the lockName can be constant and the key can be configurable e.g. a property. In this way, the key can be configured in a single place i.e. a spring bean.

This plugin needs to be used in conjunction with a concrete distributed lock plugin i.e. micro-mysql plugin.

This plugin also provides a rest end point which can be used to check if a process owns a lock for a given lockName.

## Getting The Microserver Distributed Lock Plugin

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