# Datadog metrics plugin for Microserver

[micro-metrics-datadog example apps](https://github.com/aol/micro-server/tree/master/micro-metrics-datadog/src/test/java/app/datadog/metrics)

This adds support for sending the metrics to Datadog if an api key is provided in the application.properties file. For more detailed info see [metrics-datadog](https://github.com/coursera/metrics-datadog/)

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-metrics-datadog/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-metrics-datadog)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-metrics-datadog</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-metrics-datadog:x.yz'
    
 # Configuring datadog metrics Reporters
 
 By default we report to the datadog every second. with the tag stage:dev. To modify these settings, change the values in the application.properties file.


       
		         		  