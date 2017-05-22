# Log Streamer plugin for Microserver

[micro-log-streamer example apps](https://github.com/aol/micro-server/tree/master/micro-log-streamer/src/test/java/app)

The micro-log-streamer plugin allows log files from a service to be streamed & tailed remotely via a REST endpoint.

A target log file must be configured by the property  

    log.tailer.file.location

Optionally defining a Spring bean that implements com.aol.micro.server.log.LogLookup will allow an unrestricted number of alias' for accessible files to be defined.

Clients can't access log files (or other files) directly, only the files configured by engineers building the service.


## Examples

### Streaming the primary configured file

To listen to the stream of data being added to our primary configured log file

```java
new ReactiveRequest(10, 10).getTextStream("http://myhost:8080/my-app/log-tail/stream")
                           .forEach(System.out::println)
```

Or via curl 
```
curl -v http://myhost:8080/my-app/log-tail/stream
```

### Streaming the files using alias


To listen to the stream of data being added to one of our configured log files


```java
@Component
public class CustomAliases implements LogLookup {
    
    private final Map<String,File> configuredAliases;
    
    public File lookup(String alias){
        return configuredAliases.get(alias);
    }

}
```

```java
new ReactiveRequest(10, 10).getTextStream("http://myhost:8080/my-app/log-tail/stream-file?alias=custom")
                           .forEach(System.out::println)
```


Or via curl 
```
curl -v http://myhost:8080/my-app/log-tail/stream-file?alias-custom
```

## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-reactive/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-reactive)

Simply add to the classpath

Maven 
 ```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-log-streamer</artifactId>
        <version>x.yz</version>
     </dependency>
 ```    
Gradle
 ```groovy
    compile 'com.aol.microservices:micro-log-streamer:x.yz'
 ```
 
