# General Exception Mapper Plugin Plugin

[Example General Exception Mapper Apps](https://github.com/aol/micro-server/tree/master/micro-general-exception-mapper/src/test/java/app)

Plugin that adds  general exception mapping capability

## The default mappings

```java
EOFException.class ->  Status.BAD_REQUEST
JsonProcessingException.class ->  Status.BAD_REQUEST
```

## Custom Extensions

Implement the interface com.aol.micro.server.general.exception.mapper.ExtensionMapOfExceptionsToErrorCodes in one of your Spring Beans.

### Example custom extension

```java
@Component
public class MappingExtension implements ExtensionMapOfExceptionsToErrorCodes {

	@Override
	public LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> getErrorMappings() {
		LinkedHashMap<Class<? extends Exception>, Tuple2<String, Status>> map = new LinkedHashMap<>();
		map.put(MyException.class, Tuple.tuple("my-error",Status.BAD_GATEWAY));
		return map;
	}

}
```



## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-general-exception-mapper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-general-exception-mapper)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-general-exception-mapper</artifactId>
        <version>x.yz</version>
     </dependency>
```     
Gradle
```groovy
    compile 'com.aol.microservices:micro-general-exception-mapper:x.yz'
```
