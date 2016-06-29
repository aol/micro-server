# S3 Plugin

This adds a facility to use AmazonS3Client. The following APIs are provided

1. S3ManifestComparator : Only download changed datasets from S3
2. S3ObjectWriter : For writing Java Objects to S3
3. S3StringWriter : For writing UTF-8 Strings to S3
4. S3Reader : For reading data from S3
5. S3Deleter : For deleting data from S3
6. S3Utils : General S3 uploading / downloading utils. Creational entry point for the various CRUD Apis (S3ObjectWriter, S3StringWriter, S3Reader, S3Deleter)

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
This plugin simply provides an AmazonS3Client implementation bean. You should just fill properties
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
