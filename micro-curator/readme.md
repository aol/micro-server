# Curator Plugin

This adds a facility to use curator locks.

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-curator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-curator)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-curator</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-curator:x.yz'
```
## Example api for taking lock

 ```java
@Rest(isSingleton=true)
@Path("/status")
public class CuratorStatusResource {

	private final DistributedLockService service;
	private final DistributedLockService service2;
	
	@Autowired
	public CuratorStatusResource(CuratorDistributedLockServiceProvider provider) {
		this.service = provider.getDistributedLock(1_000);
		this.service2 = provider.getDistributedLock(1_000);
	}
	@GET
	@Path("/lock")
	public String lock() {
		if(service.tryLock("hello2"))
			return "got";
		return "not";
		
	}

	@GET
	@Path("/lock2")
	public String lock2() {
		if(service2.tryLock("hello2"))
			return "got";
		return "not";
		
	}
	
}
 ```

If you'll call one of method, it will be returning "got", if you'll switch to another it will be returning "not" since they are both aiming to the same lock. DistributedLockService object itself **is a** lock, fact that `service` hold the lock, doesn't grant `service2` in any thread to obtain lock with the same name and vice versa. Despite the fact they use same name, from viewpoint of curator they are different locks.
Plugin allow you to have multiple number of locks using single connection.
