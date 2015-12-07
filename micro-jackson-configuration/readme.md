# Jackson & Jackons Configuration Plugin

[micro-jackson-configuration example apps](https://github.com/aol/micro-server/tree/master/micro-grizzly/src/test/java/app/jackson)


Add Jackson Serialization to Microserver apps & add custom configurations as neccessary.

## Configurable Properties

properties = default


jackons.seriliazation=NON_NULL


## Creating your own configuration extensions bean

To customize your Jackson configuration create a Spring bean that implements com.aol.micro.server.jackson.JacksonMapperConfigurator, it accepts the Jackson Mapper as it is being built.

For example to set Fail on Null For Primitives (or any other Serialization / Deserialization setting)

```java

@Component
public class MapperExtension implements JacksonMapperConfigurator {

	@Override
	public void accept(ObjectMapper t) {
		t.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		
	}

}

```

## NB Custom Jackson Configuration inside a Spring Context

Custom extensions are  only guaranteed to be available once the configurations have run inside a Spring context. I.e. - to use custom extensions with AsyncRestClient or RestClient in micro-client, please create instances as Spring Beans (or at least don't instantiate your instances until Spring has completed initialisation). (Default configuration will be available via the new keyword).

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jackson-configuration/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jackson-configuration)

Simply add to the classpath

Maven 
```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-jackson-configuration</artifactId>
        <version>x.yz</version>
     </dependency>
``     
Gradle
```groovy
    compile 'com.aol.microservices:micro-jackson-configuration:x.yz'
```
