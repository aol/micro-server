# CORS Plugin

[micro-cors example apps](https://github.com/aol/micro-server/tree/master/micro-cors/src/test/java/app)

Set response headers for Cross Domain support via a Servlet Filter. Two CORS Filters are provided - simple and the more advanced Ebay CORS Filter. The Ebay CORS Filter is used by default.

To select between them use the property cors.simple

      cors.simple=true
      
or for the [ebay CORS Filter](https://github.com/eBay/cors-filter)

     cors.simple=false
     

## Configuration
     
This simple CORS Filter sets the following headers

    Access-Control-Allow-Origin:"*"
    Access-Control-Allow-Methods:"GET, POST, DELETE, PUT"
    Access-Control-Allow-Headers:"X-Requested-With, Content-Type, X-Codingpedia

The Ebay CORS Filter offers much more configuration options  [https://github.com/eBay/cors-filter](https://github.com/eBay/cors-filter). You can optionally create a Spring bean of type Map<String,String> with the name ebay-cors-config to configure the Filter init params descibed on the Ebay CORS Filter Github site.



## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-cors/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-cors)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-cors</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.oath.microservices:micro-cors:x.yz'
