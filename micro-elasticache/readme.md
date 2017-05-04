# Elasticache plugin for BASE microservices

[micro-elasticache example apps](https://github.com/aol/micro-server/tree/master/micro-elasticache/src/test/java/app)

Basically Available Soft statE

* Simple Couchbase Client (Couchbase bucket as distributed / persistent map)
* Manifest comparator : Versioned key for loading refreshed state

# Manifest comparison

Manifest comparison stores a manifest along with each value. The manifest contains the version for the value, if the version has changed, the latest verson of the value will be loaded.

   
      key : manifest [contains version info]
      versionedKey : key with version 
      
This allows large immutable datastructures to be stored in as a key/value pair (with separate key/value pairing for version info), and reloaded automatically on change. 

## Injecting the manifest comparator

Inject the Spring bean created by micro-couchbase ManifestComparator into your own beans. Customise the key used (allows multiple ManifestComparators to be used)

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

@Component
public class DataCleaner  implements ScheduledJob<Job>{
	
	private final ManifestComparator<String> comparator;
	@Autowired
	public  DataCleaner(ManifestComparator comparator) {
		this.comparator = comparator.<String>withKey("test-key");
	}
	@Override
	public SystemData<String,String> scheduleAndLog() {
		try{
		        comparator.cleanAll();
			return SystemData.<String,String>builder().errors(0).processed(1).build();
		}catch(Exception e){
			return SystemData.<String,String>builder().errors(1).processed(0).build();
		}
		
	}

}

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
  
## Configurable properties

Key used to store data used by the configured ManfiestComparator in Couchbase (default is default-key)

couchbase.manifest.comparison.key=default-key

Comma separate list of Couchbase servers

couchbaseServers=

Couchbase bucket (default is couchbase_bucket) to store / read data in

couchbaseBucket=couchbase_bucket

Couchbase password

couchbasePassword=

Switch off Couchbase enabled / disabled

couchbaseClientEnabled:true

Couchbase client operation

couchbaseClientOperationTimeout:120000

## Getting The Microserver Couchbase Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-couchbase/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-couchbase)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-couchbase</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle
```groovy
    compile 'com.aol.microservices:micro-couchbase:x.yz'
 ```
