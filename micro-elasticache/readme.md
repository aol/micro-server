# Elasticache plugin for BASE microservices

[micro-elasticache example apps](https://github.com/aol/micro-server/tree/master/micro-elasticache/src/test/java/app)

Basically Available Soft statE

* Simple Memcached Client (ElastiCache as distributed / persistent map)

  
## Configurable properties

Key used to store data used by the configured ManfiestComparator in Couchbase (default is default-key)

elasticache.hostname is configuration endpoint of the elasticache cluster
elasticache.portis the port of the elasticache cluster
elasticache.retry.after.seconds is the number of seconds between each retry
elasticache.max.retries is the maximum number of retries before client throws error and gives up


## Getting The Microserver Couchbase Plugin


### Maven
```xml
     <dependency>
        <groupId>com.oath.microservices</groupId>
        <artifactId>micro-elasticache</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle
```groovy
    compile 'com.oath.microservices:micro-elasticache:x.yz'
 ```
