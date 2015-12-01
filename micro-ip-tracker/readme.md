# IP Tracker Plugin

Capture the IP address on incoming REST Requests via a filter.

The IP Address is stored in a thread local variable & available via QueryIPRetriever.getIPAddress();

## Configuration

The IP Tracker will pull client IP Addresses forwarded via a vip. By default the following headers are checked

    X-LB-Client-IP
    X-Forwarded-For

To add an additional header use the property 


     load.balancer.ip.forwarding.header=

By default all incoming requests are tracked, an array of endpoints can be speficied via the property ip.tracker.mappings

     ip.tracker.mappings=/*,/path1/*


## To use

Simply add to the classpath

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-ip-tracker/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-ip-tracker)




### Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-ip-tracker</artifactId>
        <version>x.yx</version>
     </dependency>
     
### Gradle

    compile 'com.aol.microservices:micro-ip-tracker:x.yz'