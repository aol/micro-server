# AliveHandler Plugin

Problem of implementing alive handler often arise in production tasks (either it required by load balancer or by monitoring system). This module can solve this problem by providing simple framework for building this handlers.

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-alive-handler/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-alive-handler)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-alive-handler</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-alive-handler:x.yz'
```
## Usage
Since requirements for alive handlers can vary between systems, you should write your own handling logic. It can be done by implementing AliveHandlerController. Next example provide alive handler which will just return code 200 if service is enabled, or 404 if not.
```java
@Component
public class AliveHandlerControllerApi implements AliveHandlerController {

	private volatile boolean enabled = true;
	
	@Override
	public Response process() {
		if(enabled) {
			return Response.ok().build();
		} else {
			return Response.status(404).build();
		}
	}

	@Override
	public void enable() {
		enabled = true;
	}

	@Override
	public void disable() {
		enabled = false;
	}

}
```
Since there could be multiple services bundled together, you also should write repository, which contains all of your AliveHandlerControllers with their urls. For example
```java
@Component
public class AliveHandlerControllerRepositoryImpl implements AliveHandlerControllerRepository {
	private Map<String, AliveHandlerController> map = ImmutableMap.of("ping", new AliveHandlerControllerApi());

	@Override
	public Optional<AliveHandlerController> get(String name) {
		System.out.println(name);
		return Optional.ofNullable(map.get(name));
	}
	
}
```
So in previous case, if base rest resource address is "/services/test", "/services/test/ping" will be your alive handler url.
