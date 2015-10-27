# Reactive plugin for Microserver

[micro-reactive example apps](https://github.com/aol/micro-server/tree/master/micro-reactive/src/test/java/app)

Also can run standalone outside of Microserver

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
    compile 'com.aol.microservices:micro-reactive:0.62'
 ```
 
## Reactive mixin

implement com.aol.micro.server.reactive.Reactive to add Reactive functionality via [simple-react](https://github.com/aol/simple-react).

## single-threaded (optionally non blocking)

**sync** synchronous execution of a FutureStream, but not on the calling thread (i.e. can be non-blocking to the calling thread via the run method). Would get it's own thread to execute on from a pool.

Find active queries (making use of @Suspended AsyncResponse asyncResponse NIO Rest interface)

    this.sync(lr-> lr.of((type == null ? "default" : type))
							.map(typeToUse->activeQueries.get(typeToUse).toString())
							.peek(result->asyncResponse.resume(result)))
							.run();

## multi-threaded (optionally non blocking)

**IOStream** for creating IO bound Streams. The first action is be async (i.e. tasks passed to task executor), subsequent tasks can execute synchronously on the calling thread, via the sync operator. Note the first action doesn't have to be IO related, the first stage being async distributes the work to separate workers. Users can manually change this behaviour via async / sync operators on FutureStreams. (Can be non-blocking to the calling thread)

Load a Resource from the classpath  (making use of @Suspended AsyncResponse asyncResponse NIO Rest interface)

    this.ioStream().of("/META-INF/MANIFEST.MF")
                   	.sync()
			.map(url->context.getResourceAsStream(url))
			.map(this::getManifest)
			.peek(result->asyncResponse.resume(result))
			.run();
 

**cpuStream** for creating CPU bound streams, would probably reuse the common ForkJoinPool, first action would be async (i.e. tasks passed to a task executor to distribute the work load), subsequent tasks could execute synchronously on the calling thread via the sync operator. Users can manually change this behaviour via async / sync operators on FutureStreams. (Can be non-blocking to the calling thread) 

Find active jobs currently running (making use of @Suspended AsyncResponse asyncResponse NIO Rest interface)

    this.cpuStream().of(this.activeJobs)
    			.sync()
			.then(JobsBeingExecuted::toString)
			.then(str->asyncResponse.resume(str))
			.run();

**switchIO** could be used to switch a Stream optimised for CPU bound execution into one optimised for IO Bound execution

**switchCPU** could be used to switch a Stream optimised for IO bound execution into one optimised for CPU Bound execution

## Inter-thread communiction via Queues / Topics

The Pipes class can manage simple-react Adapters (Queues / Topics and Signals). Example using an Agrona wait-free Queue (with mechanical sympathy) :

Register your Pipe (bounded non blocking Queue) which returns a LazyFutureStream for infinite processing & start our server. Any events with the Key  "test" passed into our Pipes class (via Reactive.enqueue for example) will be passed straight to our processing Stream.
 ```java
	public static void main(String[] args){
	    LazyFutureStream<String> stream = Pipes.register("test", QueueFactories.
	                                        <String>boundedNonBlockingQueue(100)
	                                            .build());
	    stream.filter(it->it!=null).peek(System.out::println).run();
	    new MicroserverApp(()-> "simple-app").run();
	}
 ```
NB - in practice with the current version - unless you expect to have a high throughput of data this implementation will be very inefficient (a blocking queue would in many cases perform better). v0.99 of simple-react will introduce native 'wait' strategies for Queues which will perform better than the custom simple-react filter we are using here. To create a pipe with a blocking queue :
 ```java
     Pipes.register("unbounded", new Queue());  // unbounded
     Pipes.register("bounded", QueueFactories.boundedQueue(1000)); //bound size 1000
 ```
Elsewhere in our application we can pass data to our Pipe (e.g. from a REST request, incoming data from an Aeron or Kafka Queue, Scheduled job etc)
 ```java
	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
	    this.enqueue("test","ping : " + next++);
	    return "ok";
	}
 ```	
In this example our processing Stream will simple print 

    ping : 0 
    ping : 1
    ping : 2
    ping : 3
    
And so on..
    

