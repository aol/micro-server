# S3 Plugin

This adds a facility to use AmazonS3Client.

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-s3/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-s3)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-s3</artifactId>
        <version>x.yz</version>
     </dependency>
```   
Gradle
```groovy
    compile 'com.aol.microservices:micro-s3:x.yz'
```
## Usage
This plugin simply provide AmazonS3Client implementation bean. You should just fill properties
s3.accessKey, s3.secretKey and s3.sessionToken (optionally - only for short term keys)
[AmazonS3Client](http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html)

```java
@Component
public class S3DAO{

     private final AmazonS3Client client;
     
     public S3DAO(AmazonS3Client client){
         this.client = client;
     }
     
     
}
```
