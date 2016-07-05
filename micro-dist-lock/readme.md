# Distributed lock plugin

[micro-dist-lock example apps](https://github.com/aol/micro-server/tree/master/micro-dist-lock/src/test/java/app)

This plugin provides some common code for distributed lock. This plugin can be used in two ways:

## Via LockKeyProvider Spring Beans

Clients only need to provide lockName and key i.e. implements the LockKeyProvider interface and then autowire the LockController.
The idea here is that the lockName can be constant and the key can be configurable e.g. a property. In this way, the key can be configured in a single place i.e. a spring bean. An example can be found in DistLockRunnerTest for the spring way.

E.g. We can define a KeyProvider as follows 

```java
@Component
public class KeyProvider implements LockKeyProvider {
    
    @Value("${my.key:key}")
    String key;
    
    @Override
    public String getKey() {
        return key;
    }
    
    @Override
    public String getLockName() {
        return "LOCK_NAME";
    }
}
```

This plugin adds a Rest end point http://<host>:<port>/<context>/dist/lock/own/lock/<LOCK_NAME> that allows users to check whether or not this Service has / or can acquire the lock successfully.


## Via Direct Instantiation

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