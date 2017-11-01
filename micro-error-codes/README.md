# micro-error-codes Plugin

A plugin for standardized error handling and health checking across your Microservices

[micro-error-codes example apps](https://github.com/aol/micro-server/tree/master/micro-error-codes/src/test/java/app)

This plugin provides a core ErrorCode class for representing errors, and provides an Abstract BaseException (along with an implementation InvalidStateException). class that ensures error codes are attached to Exceptions.

All Errors created under this system are published to an EventBus (a Guava EventBus). The micro-error-codes health checker subscribes to this event bus to determine system help. Users can attach other listeners also.

A set of Rest end points is provided to 

1. Check system health (Untested, Ok, Errors, Fatal)
2. Show recoverable and fatal errors
3. Set max number of showable errors

## Properties

Time to return the system from Error state to Ok after the last generally recoverable error (Default is 360000 millis - which is 1 hour)

health.check.time.threshold.from.error.to.normal=360000

Number of errors to show in show errors Rest end point (default is 25)

health.check.error.list.size=25

As this is configurable via the Rest end point, users can also set a hard maximum beyond which Rest configuration can not increase the number of retained errors (default is 2500)

health.check.max.error.list.size=2500

Users can switch off the facility to make recent errors and fatal errors available over Rest

health.check.show.errors=true

## Rest End points

### Health status 

GET : text/plain
<hostname>:<port>/<context>/system-errors/status

Returns one of Untested, Ok, Errors, Fatal


### System errors 

GET : application/json
<hostname>:<port>/<context>/system-errors/errors

Returns a view into the most recent recoverable and fatal errors

### Set max number of errors to show 

POST : application/json
<hostname>:<port>/<context>/system-errors/max-errors/{maxErrors}

Note this can not go past the hard maximum configurable by property

### Get max number of errors to show 

GET : application/json
<hostname>:<port>/<context>/system-errors/max-errors


# Defining ErrorCodes

Create a class or interface per service to hold your error code constants.

```java

public interface Errors {
    public final static ErrorCode QUERY_FAILURE = ErrorCode.error(1, "User {0} missing", Severity.CRITICAL);
    public final static ErrorCode SYSTEM_FAILURE = ErrorCode.error(2, "data {0} mismatch, corruption likely", Severity.FATAL);
}

```

Error Codes have three components

1. An Error Code Id (should be unique per Microservice or even across your system)
2. A text description of what happened, parameters can be provided using {0} {1} {2} etc. placeholders
3. A severity level, Errors at Fatal severity level are treated and stored separately. The system is regarded as unrecoverable at Fatal level.

# Using error codes

Simply throw and InvalidStateException passing the ErrorCode (formated to include any parameters) 

```java

throw new InvalidStateException(Errors.QUERY_FAILURE.format(userId));

```

and optionally include an Exception if handling a caught Exception
```java

try{

}catch(Exception e){
        throw new InvalidStateException(Errors.SYSTEM_FAILURE.format(systemRecord),e);
}
```

## Getting The Microserver Error Codes

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-error-codes/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-error-codes)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-error-codes</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle
```groovy
    compile 'com.aol.microservices:micro-error-codes:x.yz'
 ```
