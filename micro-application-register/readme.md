# Application / Service Registry Client Plugin

[micro-application-register example apps](https://github.com/aol/micro-server/tree/master/micro-application-register/src/test/java/app)

This plugin turns any service into a Service Registry client (and optionally server). A scheduled job will run at configurable intervals (default is every 5 minutes) and post some info on the instance to the Service Registry server (url also configurable). The server will persist data on active services to a configurable location.

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-application-register/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-application-register)

Simply add this plugin to the classpath on your Microserver app.

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-application-registry</artifactId>
        <version>x.yz</version>
     </dependency>
```    
Gradle
```groovy
    compile 'com.aol.microservices:micro-application-registry:x.yz'
```
## Depends on

    micro-client
    micro-reactive

Import this plugin to add ServiceRegistry functionality, Rest Resource available on

    /app-path/service-registry

Functionality

    [service registry server]
    /app-path/service-registry/list     : list active services
    /app-path/service-registry/register : register an active service
    /app-path/service-registry/schedule : run the scheduled job for this instance 
    										to register with the service registry
    

## Properties

    service.registry.delay:300000
    service.registry.entry.max.live:43200000
    service.registry.dir:java.io.tmpdir/services
    service.registry.url: url to register services on (http://hostname:port/context)
    
### Configure a custom host address

Use the property, host.address to set the host address (otherwise InetAddress.getLocalHost().getHostName() is used, which may cause some problems when using containers).

	host.address=custom.host.addrress

### Use caller ip

To configure the application register to use the callers ip (rather than the senders hostname), the sender should configure host.address as follows 

	host.address=use-ip

	
### Configure a target address (e.g. a VIP or load balancer)

Use the property target.endpoint to define an end point which should be used to communicate with the service being registered.

I.e. this is not the service registry URL (see above), this is a Load balancer that should be used instead of the host that is registring itself own end point. If you have 4 services of the same type behind a load balancer, each will register their host addresses, but you can also configure a common target address, that let's clients of the service registry know not to communicate directly to each host but to use their target (or load balancer address instead).

     target.endpoint=https://www.myendpoint.com/api	
