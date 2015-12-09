# Events Plugin

[micro-events example apps](https://github.com/aol/micro-server/tree/master/micro-events/src/test/java/app)

This adds a facility to capture events such as requests, request execution and scheduled jobs. 

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-events/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-events)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-events</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-events:x.yz'
```
### Depends on

1. [micro-reactive](https://github.com/aol/micro-server/tree/master/micro-reactive)
2. [micro-guava](https://github.com/aol/micro-server/tree/master/micro-guava)

## Example resource capturing queries

 ```java
@Component
@Path("/status")
public class EventStatusResource implements RestResource {

	private final EventBus bus;
	
	@Autowired //micro-events plugin configures a Guava EventBus as a Spring bean
	public EventStatusResource(EventBus bus ){
		this.bus = bus;
	}

	@GET
	@Produces("text/plain")
	@Path("/ping")
	public String ping() {
	        //Post RequestEvents starting
		bus.post(RequestEvents.start("get", 1l));
		try{
			return "ok";
		}finally{
		        //and RequestEvents finishing
			bus.post(RequestEvents.finish("get",1l));
		}
	}

}
 ```
 
Active and recently finished events become available at https://hostname::port/context/active/requests

## Capturing scheduled Jobs

Any Spring Bean implementing com.aol.micro.server.events.ScheduledJob will have start / completion tracking for the  scheduleAndLog() method. 
Event details will be added to the eventually consistent ActiveEvents class, and recent & currently active events will be visible via the ActiveResource.
 ```java
@Component
public class Job  implements ScheduledJob<Job>{

	@Override
	public SystemData<String,String> scheduleAndLog() {
		return SystemData.<String,String>builder().errors(0).processed(2).build();
	}

}
 ```

### Job metrics

Metrics about each job are captured in a SystemData object which will be also posted to a Guava EventBus, to allow custom processors to handle the 
completion event, for example, an EventBus listener that posts info to a simple-react Queue or Topic (via the Pipes class in the micro-reactive plugin) 
or an RxJava Observable.

 ```java

    public class SystemData<K, V> {

	private final Integer processed;
	private final Integer errors;
	private final Map<K, V> dataMap;

	private SystemData(Integer processed, Integer errors, Map<K, V> dataMap) {
		this.processed = processed;
		this.errors = errors;
		this.dataMap = dataMap;
	 }
  }
``` 
## Subscribing to scheduled job events

Inject in the micro-events Guava Event Bus to your class as Spring Bean, and implement a method annotated with the Guava @Subscribe annotation that takes SystemData as a single parameter.

 ```java
   
    public class Subscriber {
    @Autowired
	public Subsciber(EventBus eventBus){
	    bus.register(this);
	}
      
        @Subscribe
        public void listenForJobEvents(SystemData data){ 
         
           logsStats(data);
        }
	
  }
```

##  Capturing Queries
 
To capture requests or Queries post a AddQuery event to the configured  Guava event bus when the Query starts, and a RemoveQuery event when it finishes. There are static helper methods on the RequestEvents class to help with this. E.g. 
 ```java

        bus.post(RequestEvents.start("get request", correlationId));
		try{
			return "ok";
		}finally{
			bus.post(RequestEvents.finish("get request",correlationId));
		}
```		
## REST Calls and output

1. **/active/requests** shows currently active and recently completed requests
2. **/active/jobs** shows currently active and recently completed jobs

### Active Requests output
 ```json
    {
    "removed": 0,
    "added": 1,
    "active": {
        "1-32": {
            "freeMemory": 135573448,
            "startedAt": 1438634550597,
            "startedAtFormatted": "2015.08.03 at 21:42:30 IST",
            "processingThread": 32,
            "correlationId": 1,
            "query": "get",
            "type": "default",
            "additionalData": null
        }
    },
    "events": 1,
    "recently-finished": [
        {
            "event": {
                "freeMemory": 135573448,
                "startedAt": 1438634550597,
                "startedAtFormatted": "2015.08.03 at 21:42:30 IST",
                "processingThread": 32,
                "correlationId": 1,
                "query": "get",
                "type": "default",
                "additionalData": null
            },
            "completed": 1438634550598,
            "completed-formated": "2015.08.03 at 21:42:30 IST",
            "time-taken": 1,
            "memory-change": 0
        }
    ]
}
```
### Active Jobs output
 ```json
    {
    "removed": 0,
    "added": 9304,
    "active": {
        "id_app.events.com.aol.micro.server.Job-15": {
            "freeMemory": 222866800,
            "startedAt": 1438634384118,
            "startedAtFormatted": "2015.08.03 at 21:39:44 IST",
            "processingThread": 15,
            "type": "app.events.com.aol.micro.server.Job",
            "timesExecuted": 9304
        },
        "id_app.events.com.aol.micro.server.Job-16": {
            "freeMemory": 115960832,
            "startedAt": 1438634382652,
            "startedAtFormatted": "2015.08.03 at 21:39:42 IST",
            "processingThread": 16,
            "type": "app.events.com.aol.micro.server.Job",
            "timesExecuted": 8143
        },
        "id_app.events.com.aol.micro.server.Job-17": {
            "freeMemory": 117375880,
            "startedAt": 1438634382107,
            "startedAtFormatted": "2015.08.03 at 21:39:42 IST",
            "processingThread": 17,
            "type": "app.events.com.aol.micro.server.Job",
            "timesExecuted": 7720
        }
    },
    "events": 9304,
    "recently-finished": [
        {
            "event": {
                "freeMemory": 220814872,
                "startedAt": 1438634371998,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 9
            },
            "completed": 1438634371999,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 1,
            "memory-change": -447624
        },
        {
            "event": {
                "freeMemory": 221845600,
                "startedAt": 1438634371996,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 8
            },
            "completed": 1438634371996,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 0,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 222293016,
                "startedAt": 1438634371992,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 7
            },
            "completed": 1438634371992,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 0,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 222740728,
                "startedAt": 1438634371988,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 6
            },
            "completed": 1438634371989,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 1,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 222753672,
                "startedAt": 1438634371985,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 5
            },
            "completed": 1438634371985,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 0,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 222753704,
                "startedAt": 1438634371982,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 4
            },
            "completed": 1438634371982,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 0,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 222753704,
                "startedAt": 1438634371979,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 16,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 3
            },
            "completed": 1438634371979,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 0,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 223648616,
                "startedAt": 1438634371977,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 15,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 2
            },
            "completed": 1438634371977,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 0,
            "memory-change": 0
        },
        {
            "event": {
                "freeMemory": 226284664,
                "startedAt": 1438634371955,
                "startedAtFormatted": "2015.08.03 at 21:39:31 IST",
                "processingThread": 15,
                "type": "app.events.com.aol.micro.server.Job",
                "timesExecuted": 1
            },
            "completed": 1438634371965,
            "completed-formated": "2015.08.03 at 21:39:31 IST",
            "time-taken": 10,
            "memory-change": -894784
        }
    ]
}
```
