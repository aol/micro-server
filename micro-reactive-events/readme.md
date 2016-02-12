# Reactive Events plugin for Microserver


Schedule micro-events jobs using cyclops-streams SequenceM stream, to capture execution. Connnect to the returned HotStream for further processing.

## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-reactive/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-reactive)

Simply add to the classpath

Maven 
 ```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-reactive-events</artifactId>
        <version>x.yz</version>
     </dependency>
 ```    
Gradle
 ```groovy
    compile 'com.aol.microservices:micro-reactive-events:x.yz'
 ```
 
```java
ScheduledJob<Job> myJob = new ScheduledJob<Job>()
JobExecutor executor = new JobExecutor(ex);

executor.schedule("* * * * * ?", myJob)
				.connect()
				.debounce(1,TimeUnit.DAYS)
				.futureOperations(Executors.newSingleThreadExecutor())
				.forEach(this::logToDB);

```
without futureOperations it will be use same thread for executing tasks.
