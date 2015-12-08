# Machine stats plugin for Microserver

Uses Sigar to capture machine stats for each service (CPU load, memory usage etc). If -Djava.library.path is not set, automatically checks ./sigar-lib for native dependencies. 

Adds a rest end point /stats/machine to view statistics about the current container or box.

[Example micro-machine-stats apps](https://github.com/aol/micro-server/tree/master/micro-machine-stats/src/test/java/app)

(Microserver 0.78 and above!)

# To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-machine-stats/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-machine-stats)

Add this plugin to the classpath, bundle you final Microserver with the unpacked Sigar native libs stored alongside the jar (or launching script) in a directory named /sigar-lib.

Maven 
 ```xml

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-machine-stats</artifactId>
        <version>x.yz</version>
     </dependency>

```    
Gradle
```groovy
    compile 'com.aol.microservices:micro-machine-stats:x.yz'
```

## Using your build system to automatically unpack Sigar

### Maven

See : http://stackoverflow.com/questions/5388661/unzip-dependency-in-maven

### Gradle

See : http://stackoverflow.com/questions/29437888/using-gradle-with-native-dependencies

and https://discuss.gradle.org/t/unzipping-and-copying-jar-dependency-content-in-desired-folder/8579
