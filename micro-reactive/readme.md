# Reactive plugin for Microserver

[micro-reactive example apps](https://github.com/aol/micro-server/tree/master/micro-reactive/src/test/java/app)

The micro-reactive plugin integrates [cyclops-react](https://github.com/aol/cyclops-react) and [Pivotal Reactor](http://projectreactor.io/) to provide a very rich integrated reactive programming environment on top of Spring. 

Why?

cyclops-react offers a range of functional datatypes and datastructures, many of which act as reactive-streams Publishers /subscribers. Pivotal Reactor offer advanced / specialized processing capabilities for reactive-streams Publishers and subscribers.

Features include

* EventQueueManager - a powerful event bus
* Enhanced for-comprehension syntax specifically for micro-reactive
* Ability to treat Flux and Mono as cyclops monads
* Job schedular - runs jobs as scheduled jobs and integrates with micro-events
* CyclopsReactor a conversion layer between cyclops-react and Reactor datatypes

## Examples

## Event bus - 

Strongly typed event bus backed by Agrona wait free or JDK blocking / non-blocking queues.

```java
bus.forEach("hello",this::eventReciever);
bus.push("hello", "world");

bus.stream("bus-2")
	  .futureOperations(Executor.newFixedThreadExecutor(1))
	  .map(this::transform)
	  .forEach(this::process);
		
bus.push("bus-2", myData1);
bus.push("bus-2", myData2);


```


## For comprehensions

 ```java
ListX<Tuple<Integer,Integer>> list = For.Publishers.each(Flux.range(1,10),
					 					   				  i-> ReactiveSeq.iterate(i,a->a+1),
										  					 Tuple::tuple)
												   .toListX();

 ```
 
## Job Schedular

 ```java
schedular.schedule("* * * * * ?", myJob)
		 .connect()
		 .debounce(1,TimeUnit.DAYS)
	     .forEach(this::logStats) 
 ```
 
 ## Reactor monads
 
 AnyMSeq wraps any sequential monad, allowing standard interfaces across Streams, Lists etc
 
  ```java
 AnyMSeq<String> flux = CyclopsReactor.from(Flux.just("hello","world","c"));
  ```
  
 ## Conversions
 
 From a Flux to a LazyFutureStream
 
 ```java
CyclopsReactor.fromFlux(Flux.just(1,2,3))
			  .toFutureStream(new LazyReact(100,100))
			   .map(this::parallelIO)
			.run();
 	
``` 
 
 From a Flux to a ListX
 
 ```java
ListX.fromPublisher(Flux.just(1,2,3));
  ```
  
 From a Flux to a ListX 2
 
```java
Flux.just(1,2,3).collect(ListX::empty,ListX::add);
 ```  
 
 From a Flux to a PStackX
 
 ```java
PStackX.fromPublisher(Flux.just(1,2,3));
  ```
  
 From a Flux to a PStackX
 
 ```java
Flux.just(1,2,3)
    .map(PStackX::of)
    .reduce(Reducers.toPStackX());
  ```  
  
## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-reactive/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-reactive)

Simply add to the classpath

Maven 
 ```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-reactive</artifactId>
        <version>x.yz</version>
     </dependency>
 ```    
Gradle
 ```groovy
    compile 'com.aol.microservices:micro-reactive:x.yz'
 ```
 
