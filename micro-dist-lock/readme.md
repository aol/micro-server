# Distributed lock plugin

[micro-dist-lock example apps](https://github.com/aol/micro-server/tree/master/micro-dist-lock/src/test/java/app)

This plugin provides some common code for distributed lock. 

## DistributedLockManager

DistributedLockManager provides a way to bundle a DistributedLock Service and the lock together, it can be instantiated directly

```java
    DistributedLockService distributedLockService;
    DistributedLockManager manager = new DistributedLockManager("key",distributedLockService);
    
    manager.isMainProcess();
```

You can create your own Spring Beans via Spring Configuration class
```java
    @Configuration
    public class MyConfig{
    
        @Autowired
        DistributedLockService distributedLockService;
        
        @Bean
        public DistributedLockManager manager(){
            return new DistributedLockManager("key",distributedLockService);  
        }
    
    }
```



This plugin is abstract and to take effect needs to be used in conjunction with a concrete distributed lock plugin i.e. micro-mysql plugin.

This plugin also provides a rest end point which can be used to check if a process owns a lock for a given lockName or lockKey.

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