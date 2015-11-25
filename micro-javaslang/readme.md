# Javaslang Plugin for Microserver

[micro-javaslang example apps](https://github.com/aol/micro-server/tree/master/micro-javaslang/src/test/java/app)

This plugin 

1. Configures Jackson for Javaslang serialisation / deserialisation, so Javaslang types can be used as input and output to jax-rs Resources
2. Add cyclops-javaslang to your project which enables conversion between JDK / Javaslang / Guava / Jool / simple-react types and adds features such as for-comprehensions to Javaslang.
3. Integrates with micro-client, Rest clients (RestClient, AsyncRestClient and NIORestClient) automatically pick up Javaslang mappings
4. Adds some reactive programming support for Javaslang Streams namely 
     a. JavaslangReactive mixin allows creating Javaslang reactive-streams Publishers and Subsribers simple
     b. JavaslangPipes and JavaslangReactive provide integration with simple-react
     c. Ability to push data into a Javaslang Stream across threads via JavaslangPipes


	 

## To use

Simply add to the classpath

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-javaslang/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-javaslang)

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-javaslang</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-javaslang:x.yz'
    
## Examples

### JavaslangPipes

 ```java
 	
	Queue queue = QueueFactories.boundedNonBlockingQueue(1000); //set up an Agrona backed wait-free queue
	queue.add("world"); //add some data to the queue
	JavaslangPipes.register("myQueue",queue); //register the queue
	
	//on a separate thread process data streaming from the queue
	JavaslangPipes.stream("myQueue")
				  .map(this:process)
				  .forEach(this:save);
	
```
    
### Simple App with Javaslang Lists and Sets

 ```java
@Microserver
@Path("/javaslang")
@Rest
public class JavaslangApp {


	public static void main(String[] args){
		 new MicroserverApp(() -> "javaslang-app").start();

	}

	
	@GET
	@Produces("application/json")
	@Path("/ping")
	public ImmutableJavaslangEntity ping() {
		return ImmutableJavaslangEntity.builder().value("value")
				.list(List.ofAll("hello", "world"))
				.mapOfSets(HashMap.<String,Set>empty().put("key1",HashSet.ofAll(Arrays.asList(1, 2, 3))))
				.build();
	}
	
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	@XmlRootElement(name = "immutable")
	@Getter
	@AllArgsConstructor
	@Builder
	public static class ImmutableJavaslangEntity {

		private final String value;
		private final List<String> list;
		private final Map<String,Set> mapOfSets;
		
		
		public ImmutableJavaslangEntity() {
			this(null,null,null);
		}
		
	}

}    
```

Running the app and browsing to http://localhost:8080/javaslang-app/javaslang/ping

 ```json
{
    "value": "value",
    "list": [
        "hello",
        "world"
    ],
    "mapOfSets": {
        "key1": [
            1,
            2,
            3
        ]
    }
}    
 ```   
 
### For comphrension example
 
 Javaslang List and JDK8 CompletableFuture
 
 ```java 
 public void cfList(){
		
		CompletableFuture<String> future = CompletableFuture.supplyAsync(this::loadData);
		CompletableFuture<List<String>> results1 = Do.add(future)
 									    .add(List.of("first","second"))
 										.yield( loadedData -> localData-> loadedData + ":" + localData )
 										.unwrap();
		
		System.out.println(results1.join());
		
	}
	private String loadData(){
		return "loaded";
	}
 ```

