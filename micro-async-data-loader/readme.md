# Async Data Writer Plugin

[micro-async-data-loader example apps](https://github.com/aol/micro-server/tree/master/micro-async-data-loader/src/test/java/app)

This plugin supports asyncrhonously reading data from a store such as S3 or Couchbase (via micro-s3 or micro-couchbase plugins), using the Manifest Comparator pattern (see micro-manifest-comparator). This is a technique that allows a Microservice to generate data for another Microservice to use. The writer stores the data and a version in the datastore. The reader checks for version changes and asynchronously loads the data on change. With micro-async-data-writer and micro-async-data-loader in conjunction with either micro-s3 or micro-couchbase Microserver provides the infrastructure and client code need only configure data access and pass the data to the AsyncDataWriter bean. By adding micro-async-data-loader to the classpath of your consuming service (and configuring your datasource and manifest comparator key name),  changes will be automatically detected and loaded asynchronously into that service.


# Async Data Loader Plugin Features

1. Scheduled job to load data into your ManifestComparator instance
2. Load data immediately on startup
3. Integrates with micro-events to capture load events
4. Supports automatic loading from multiple configured ManifestComparator beans


## Configuring and using the AsyncDataLoader

This plugin must be used in conjunction with an implementation of the interfaces in micro-manifest-comparator (either micro-s3 or micro-couchbase should be on the classpath). The first steps to using the AsyncDataLoader should be to configure access to your data store as per the appropriate plugin (configure access keys for S3, servers / user / password for Couchbase).

To configure a manifest comparator for Couchbase please set this property

couchbase.manifest.comparison.key=<key>

To configure a manifest comparator for S3 please set this property

s3.manifest.comparator.key=<key>

### Additional properties are 

asyc.data.schedular.cron.loader=0 * * * * *

How often loader should check for changes in data (uses a quartz expression and attempts a load every minute by default).

By default data will be loaded from all configured ManifestComparators using the default cron.

asyc.data.schedular.threads=no. of threads for asynchronous loading

The default value is 5.

asyc.data.writer.multi=true / false

By default writing to multiple services is disabled, if more than one ManifestComparator bean is found on the classpath only the first is configured in an AsyncDataWriter. If asyc.data.writer.multi is set to true all ManifestComparators will be wrapped by a single MultiDataWriter bean. Calling saveAndIncrement with new data on this bean will write that data to all sources.

### Configuring multiple ManifestComparators 

micro-s3 or micro-couchbase will each configure a default ManifestComparator bean. You can create new ManifestComparator instances that will store/read data under a different key within the same S3 or couchbase bucket via ManifestComparator#newKey.

e.g.

 ```java
 
     @Configuration
     public MySpringConfig{
        
        @Autowired
        private ManifestComparator mainComparator;
         
        @Bean
        public ManifestComparator<DataType2> comparator2(){
            return mainComparator.withKey("key2");
        }
      }  
 ```

### Configuring loading from Multiple ManifestComparators on separate crons

If you have configured multiple ManifestComparator and want to check for changes to the data on differing schedules, simply configure a DataLoader that contains the ManifestComparator e.g.

```java

     @Configuration
     public MySpringConfig{
        
        @Value("${my.data.schedular.cron.loader2:0 * * * * *}")
        private String dataLoader2Cron;
        @Autowired
        private ManifestComparator mainComparator;
         
        @Bean
        public ManifestComparator<DataType2> comparator2(){
            return mainComparator.withKey("key2");
        }
        @Bean
        public DataLoader dataLoader2(){
            return new DataLoader(comparator2(),dataLoader2Cron);
        }
      }  
```


## Accessing data which has been loaded asynchronously

Simply inject your ManifestComparator Bean into the service that needs the data and call getData!

```java

    @Service
    public class MyService{
    
        private final ManifestComparator<Data> mc;
        
        public MyService(ManifestComparator<Data> mc){
            this.mc = mc;
        }
        
        public ProcessedData processData(Processor processor){
            //data is loaded asynchronously into the ManifestComparator by micro-async-loader
           return processor.process(mc.getData());
        }
    
    }

```

## Getting The Microserver Async Data Loader Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-async-data-loader/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-async-data-loader)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-async-data-loader</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle
```groovy
    compile 'com.aol.microservices:micro-async-data-loader:x.yz'
 ```
