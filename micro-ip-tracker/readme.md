# IP Tracker Plugin

Capture the IP address on incoming REST Requests via a filter.

The IP Address is stored in a thread local variable & available via QueryIPRetriever.getIPAddress();

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