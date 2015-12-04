# Transactions Plugin

Handle transactions in a more fluid stream-like (&Java8 Style) fashion

 ```java
Integer result = TransactionFlow.of(transactionTemplate, this::load)
										.map(this::save)
										.execute(10)
										.get();
```				

Inject in a preconfigured TransacitonFlow bean
 ```java
 
 private final TransactionFlow flow;
 @Autowired
 public MyService(TransactionFlow flow){
   this.flow = flow;
 } 
 
```	

The map and flatMap your heart out. All mapped / flatMapped functions will be executed in the scope of a transaction.

 ```java
 
flow.map(this::nextAvailable)
    .map(this::updateStatus)
    .execute(id);
```	

TransactionFlow is modelled on the Reader monad (eek!) - which allows for lazy evaluation and dependency injection (hurray!)

[Dependency injection using the Reader monad in Java 8](https://medium.com/@johnmcclean/dependency-injection-using-the-reader-monad-in-java8-9056d9501c75#.3wxsz9n2z)
						
## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-transactions/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-transactions)

Simply add to the classpath

Maven 

```xml
     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-transactions</artifactId>
        <version>x.yz</version>
     </dependency>
```  
  
Gradle
```gradle
    compile 'com.aol.microservices:micro-transactionsy:x.yz'
```
