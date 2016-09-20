# Async Data Writer Plugin

[micro-async-data-writer example apps](https://github.com/aol/micro-server/tree/master/micro-async-data-writer/src/test/java/app)

This plugin supports asyncrhonously writing data to a store such as S3 or Couchbase (via micro-s3 or micro-couchbase plugins), using the Manifest Comparator pattern (see micro-manifest-comparator). This is a technique that allows a Microservice to generate data for another Microservice to use. The writer stores the data and a version in the datastore. The reader checks for version changes and asynchronously loads the data on change. With micro-async-data-writer and micro-async-data-loader in conjunction with either micro-s3 or micro-couchbase Microserver provides the infrastructure and client code need only configure data access and pass the data to the AsyncDataWriter bean. By adding micro-async-data-loader to the classpath of your consuming service (and configuring your datasource and manifest comparator key name),  changes will be automatically detected and loaded asynchronously into that service.


# Async Data Writer Plugin Features

1. AsyncDataWriter for asynchronous data storage
2. DataCleaner for scheduled removal of old data keys
3. MultiDataWriter for data migration (supports writing to multiple stores at once)
4. All updates and loads trigger events written to a Guava Event Bus

## Configuring and using the AsyncDataWriter

This plugin must be used in conjunction with an implementation of the interfaces in micro-manifest-comparator (either micro-s3 or micro-couchbase should be on the classpath). The first steps to using the AsyncDataWriter should be to configure access to your data store as per the appropriate plugin (configure access keys for S3, servers / user / password for Couchbase).


### Additional properties are 

async.data.writer.threads=no. of threads for asynchronous writing

1 Thread is the default and is generally enough unless multiple AsyncDataWriters are to be configured to use the same thread pool.

async.data.writer.multi=true / false

By default writing to multiple services is disabled, if more than one ManifestComparator bean is found on the classpath only the first is configured in an AsyncDataWriter. If async.data.writer.multi is set to true all ManifestComparators will be wrapped by a single MultiDataWriter bean. Calling saveAndIncrement with new data on this bean will write that data to all sources.

### Using AsyncDataWriter

Inject in the AsyncDataWriter as a Spring Bean to your Service

 ```java
	 private final AsyncDataWriter<String> comparator;
	
	@Autowired
	public  MyService(AsyncDataWriter comparator) {
		this.comparator = comparator;
	}
	
 ```
 
 Save data to the remote store via saveAndIncrement
 
  ```java
 FutureW<Void> task = comparator.saveAndIncrement(newData);
 ```
 
 All methods on AsyncDataWriter return a FutureW and execute asynchronously. Users can block by calling get() on the FutureW task and recieve any result (Void tasks will return null). Additional operations can be chained via map / peek etc.
 
 ### Using MultiDataWriter
 
 Both AsyncDataWriter and MultiDataWriter implement the same interface DataWriter. Simply inject the MultiDataWriter into your Service bean and turn on the feature.
 
 ```java
	 private final DataWriter<String> comparator;
	
	@Autowired
	public  MyService(MultiDataWriter comparator) {
		this.comparator = comparator;
	}
	
 ```
 
 We can save the data to all configured stores via saveAndIncrement
 
  ```java
 FutureW<Void> task = comparator.saveAndIncrement(newData);
 ```
 
 ### Configuring multiple different writers
 
 Best practice is to configure one writer per Microservice, but if you really need more, they can be configured independently as Spring Beans or Programmatically.
 
Use AsyncDataWriter#asyncDataWriter to create a new instance - passing in an Executor, a ManifestComparator and optionally a Guava event bus.

  ```java
AsyncDataWriter<Data> dataWriter = AsyncDataWriter.asyncDataWriter(executor,manifestComparator,eventBus);
 ```

New ManifestComparators targeting a different data key can be created from preconfigured ManifestComparators via ManifestComparator#withKey


 ```java
 
 public ManifestComparator<NewData> createManifestComparatorFromExisting(ManifestComparator<OldData> existing){
   return existing.withKey("new-data-key");
 }
 
```
 
# Data Cleaner Plugin Features

The Data Cleaner removes old versions of the versioning key from the data store (Couchbase or S3). It runs on a schedule configurable via a Quartz cron expression. The default is to run hourly -

async.data.schedular.cron.cleaner=0 0 * * * ?

When the cleaner runs it triggers an event with processing and error stats published to a Guava Event Bus.

## Conditionally turning the cleaner off

The DataCleaner can be truned off conditionally by having a Spring Bean implement ConditionallyClean. shouldClean is called everytime the DataCleaner is scheduled to determine if it is run.

```java
 
@Component
public class TurnOff implements ConditionallyClean {

    @Override
    public boolean shouldClean() {

        return false;
    }

}

```

## Getting The Microserver Async Data Writer Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-async-data-writer/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-async-data-writer)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-async-data-writer</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle
```groovy
    compile 'com.aol.microservices:micro-async-data-writer:x.yz'
 ```
