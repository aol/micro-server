# Distributed lock plugin for MySQL

Allows MySQL to be used to create distributed locks. Not suitable for use against a MySQL cluster prior to version .

Autowire com.aol.micro.server.utility.DistributedLockService into your beans to make use of Distributed locking. If you also have the couchbase plugin installed autowire com.aol.micro.server.mysql.distlock.DistributedLockServiceMySqlImpl instead.

## Getting The Microserver MySql Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-mysql/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-mysql)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-mysql</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle

    compile 'com.aol.microservices:micro-mysql:x.yz'