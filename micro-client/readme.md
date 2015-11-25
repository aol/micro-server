# REST client plugin for Microserver

[micro-client example apps](https://github.com/aol/micro-server/tree/master/micro-client/src/test/java/app)

This plugin provides thre REST Clients

1. NIORestClient - which is a non-blocking REST client using NIO, wrapper over Apache Async HTTP. Modified to return Java 8 CompletableFuture objects.
2. AsyncRestClient - a wrapper over the Async Jersey Rest Client (it is asyncrhonous, but makes use of threads rather than NIO). Modified to return Java 8 CompletableFuture objects, and enhanced with withXXXX methods for configuration purposes.
3. RestClient - which syncrhonous wrapper over the Jersey Rest Client, enhanced with withXXXX methods for configuration purposes

The NIORestClient is available as  Spring bean. AsyncRestClient & RestClient can simply be instantiated via the new keyword.

## Example AsyncRestClient
 ```java
public class Example{


	private final int readTimeout = 10_000;
	private final int connnectionTimeout = 1_000;
	private final AsyncRestClient<Result> restClient = new AsyncRestClient<>(readTimeout,connectionTimeout);;
	private final String url;
	private final String ACCEPT_HEADERS;
	private final String CONTENT_HEADERS;

	public CompletableFuture<Result> query(Payload payload){
		
		return this.restClient.withAccept(ACCEPT_HEADERS)
						.withContentType(CONTENT_HEADERS)
						.withResponse(RESULT.class)
						.post(url,payload);
	
	}

	
}
```
## Example RestClient
 ```java
public class Example{


	private final int readTimeout = 10_000;
	private final int connnectionTimeout = 1_000;
	private final RestClient<Result> restClient = new RestClient<>(readTimeout,connectionTimeout);
	private final String url;
	private final String ACCEPT_HEADERS;
	private final String CONTENT_HEADERS;

	public Result query(Payload payload){
		
		return this.restClient.withAccept(ACCEPT_HEADERS)
						.withContentType(CONTENT_HEADERS)
						.withResponse(RESULT.class)
						.post(url,payload);
	
	}

	
}
```
## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-client/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-client)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-client</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-client:x.yz'
