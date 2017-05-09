# S3 Plugin

[micro-S3 example apps](https://github.com/aol/micro-server/tree/master/micro-S3/src/test/java/app)

This adds a facility to use AmazonS3Client. The following APIs are provided

1. S3ManifestComparator : Only download changed datasets from S3
2. S3ObjectWriter : For writing Java Objects to S3. SSE AES236 available.
3. S3StringWriter : For writing UTF-8 Strings to S3. SSE AES236 available.
4. S3Reader : For reading data from S3
5. S3Deleter : For deleting data from S3
6. S3Utils : General S3 uploading / downloading utils. Creational entry point for the various CRUD Apis (S3ObjectWriter, S3StringWriter, S3Reader, S3Deleter)
7. DistributedMap implementation that uses S3 as distributed persistent map

See also [micro-async-data-writer](https://github.com/aol/micro-server/tree/master/micro-async-data-writer) and [micro-async-data-loader](https://github.com/aol/micro-server/tree/master/micro-async-data-loader) for automated management of writing versioned data and loading versioned data from S3 (or couchbase via micro-couchbase), that builds on this plugin.

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-s3/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-s3)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-s3</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-s3:x.yz'
```

# Manifest comparison

Manifest comparison stores a manifest along with each value. The manifest contains the version for the value, if the version has changed, the latest verson of the value will be loaded.

Configure the S3 Bucket for Manifest comparision via this property

s3.manifest.comparator.bucket

You can also configure the key under which your data will be stored with

s3.manifest.comparator.key:default

   
      key : manifest [contains version info]
      versionedKey : key with version 
      
This allows large immutable datastructures to be stored in as a key/value pair (with separate key/value pairing for version info), and reloaded automatically on change. 

## Injecting the manifest comparator

Inject the Spring bean created by micro-s3 ManifestComparator into your own beans. Customise the key used (allows multiple ManifestComparators to be used)

```java
public class ManifestComparatorResource {
	

	private final ManifestComparator<String> comparator;
	@Autowired
	public  ManifestComparatorResource(ManifestComparator comparator) {
		this.comparator = comparator.<String>withKey("test-key");
	}
```

## Create a scheduled job

See micro-events, for Microserver help in managing scheduled jobs.

Create a scheduled job to check the manifest for changes & automatically reload data when stale.

In the example below we run the versioned key cleaner once per day, and check for changes every minute.

 ```java
@Component
public class DataLoader  implements ScheduledJob<Job>{
	
	private final ManifestComparator<String> comparator;
	@Autowired
	public  DataLoader(ManifestComparator comparator) {
		this.comparator = comparator.<String>withKey("test-key");
	}
	@Override
	public SystemData<String,String> scheduleAndLog() {
		try{
			boolean changed = comparator.isOutOfDate();
			comparator.load();
			return SystemData.<String,String>builder().errors(0).processed(isOutOfDate?1:0).build();
		}catch(Exception e){
			return SystemData.<String,String>builder().errors(1).processed(0).build();
		}
	}

}
 ```
 
 ```java

@Component
public class Schedular{

     private final DataCleaner cleaner;
     private final DataLoader loader;
     
     public Schedular(DataCleaner cleaner,DataLoader loader){ 
     	this.cleaner = cleaner;
        this.loader = loader;
     }
 
 
    @Scheduled(cron = "0 1 1 * * ?")
	public synchronized void scheduleCleaner(){
		cleaner.scheduleAndLog();
	}
	@Scheduled(cron = "0 * * * * *")
	public synchronized void scheduleLoader(){
		loader.scheduleAndLog();
	}

}

 ```

Elsewhere a single writer service can write data to the store for all services of a type to load. See micro-mysql or micro-curator for a DistributedLock implementation

e.g.

 ```java
 @Component
 public class DataWriter {
 
 	private final DistributedLockService lockService;
 	
 	private final ManifestComparator<String> comparator;
	@Autowired
	public  DataWriter(DistributedLockService lockService,ManifestComparator comparator) {
	    this.lockService = lockService;
		this.comparator = comparator.<String>withKey("test-key");
	} 
	
	public void write(Supplier<String> data){
	   if(lockService.tryLock("single-writer-lock-a") {
	   		comparator.saveAndIncrement(data.get());
	   }
	}
	
	
	
 }
 
  ```
  
## DistributedMap
 
 Configure the S3 to be used by the DistributedMap with the following property
 
 s3.distributed.map.bucket
 
Then inject in either  DistributedMap or it's concrete implementation S3DistributedMapClient into your code to use S3 as a remote persistent Key Value store.
 
 ```java
 
 @Component
 public class MyService{
    
     private final DistributedMap<DataType> map;
     
     @Autowired
     public MyService(DistributedMap map){
       this.map = map;
     }
 
 }
 
 ```
  


## Usage AmazonS3Client

This plugin also provides an AmazonS3Client implementation bean. You should define properties for
s3.accessKey, s3.secretKey and optionall s3.sessionToken (optionally - only for short term keys)
[AmazonS3Client](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html)

Configure the S3 Bucket for Manifest comparision via this property

s3.manifest.comparator.bucket

You can also configure the key under which your data will be stored with

s3.manifest.comparator.key:default

```java
@Component
public class S3DAO{

     private final AmazonS3Client client;
     
     public S3DAO(AmazonS3Client client){
         this.client = client;
     }
     
     
}
```
