# Couchbase plugin for BASE microservices

Basically Available Soft statE

* Simple Couchbase Client
* Manifest comparator : Versioned key for loading refreshed state
* Simple Distributed lock implementation

# Manifest comparison

Manifest comparison stores a manifest along with each value. The manifest contains the version for the value, if the version has changed, the latest verson of the value will be loaded.

   
      key : manifest [contains version info]
      versionedKey : value 
      
This allows large immutable datastructures to be stored in as a key/value pair (with separate key/value pairing for version info), and reloaded automatically on change. 

# Distributed Lock

A simple distributed lock implementation that could be used to select a single leader from multiple members in a cluster.

## Getting The Microserver Couchbase Plugin

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-couchbase/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-couchbase)

### Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-couchbase</artifactId>
        <version>x.yz</version>
     </dependency>
```
### Gradle

    compile 'com.aol.microservices:micro-couchbase:x.yz'