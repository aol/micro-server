# Application / Service Registry Client Plugin

[micro-application-registry example apps](https://github.com/aol/micro-server/tree/master/micro-application-registry/src/test/java/app)

This plugin turns any service into a Service Registry client (and optionally server). A scheduled job will run at configurable intervals (default is every 5 minutes) and post some info on the instance to the Service Registry server (url also configurable). The server will persist data on active services to a configurable location.

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-application-registry/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-application-registry)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-application-registry</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-application-registry:x.yz'

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
