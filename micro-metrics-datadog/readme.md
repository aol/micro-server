# Datadog metrics plugin for Microserver

[micro-metrics-datadog example apps](https://github.com/aol/micro-server/tree/master/micro-metrics-datadog/src/test/java/app/datadog/metrics)

This adds support for sending the metrics to Datadog if an api key is provided in the application.properties file. For more detailed info see [metrics-datadog](https://github.com/coursera/metrics-datadog/)

This plugin in combination with the micro-event-metrics plugin will send some metrics to datadog by default. Refer here (https://github.com/aol/micro-server/tree/master/micro-event-metrics) and (https://github.com/aol/micro-server/blob/master/micro-event-metrics/src/main/java/com/aol/micro/server/event/metrics/MetricsCatcher.java)

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-metrics-datadog/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-metrics-datadog)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-metrics-datadog</artifactId>
        <version>x.yz</version>
     </dependency>
```     
Gradle
```groovy
    compile 'com.aol.microservices:micro-metrics-datadog:x.yz'
```
    
# Configuring datadog metrics Reporters
 
 By default we report to the datadog every second. with the tag stage:dev. To modify these settings, change the values in the application.properties file.
 For example:
 The application.peroperties file can be modifed as follows
 ```
datadog.apikey = <your api key goes here>
datadog.tags = "stage:dev", "owner:abc"
datadog.report.period = 10
datadog.report.timeunit = SECONDS
```

This will report the metrics to datadog with tags as "stage:dev" and "owner:abc" and for every 10 seconds


       
		         		  
