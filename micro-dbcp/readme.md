 # DBCP plugin

[micro-hikaricp example apps](https://github.com/aol/micro-server/tree/master/micro-jdbc/src/test/java/app)

Creates a DataSource Spring Bean with name "mainDataSource". This will be based on [DBCP2](https://commons.apache.org/proper/commons-dbcp/).

## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-data/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-jdbc)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-dbcp</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-dbcp:x.yz'

# Configuring a data source

A datasource can be configured by setting the following properties in application.properties, instance.properties or via the Microserver annotation

     db.connection.driver: (e.g. (org.hsqldb.jdbcDriver)
	 db.connection.url: (e.g. jdbc:hsqldb:mem:aname)
	 db.connection.username: (e.g. admin)
	 db.connection.password: (e.g. password)
	 db.connection.dialect:  (e.g. org.hibernate.dialect.HSQLDialect)
	 db.connection.hibernate.showsql: (e.g. true | false)
	 db.connection.ddl.auto: (e.g. create-drop)
	 dbcp.db.test.on.borrow: (e.g. true | false)
	 dbcp.db.validation.query: (e.g. SELECT 1)
	 dbcp.db.max.total: (e.g. -1)
	 dbcp.db.min.evictable.idle.time: (e.g. 1800000)
	 dbcp.db.time.between.eviction.runs: (e.g. 1800000)
	 dbcp.db.num.tests.per.eviction.run: (e.g. 3)
	 dbcp.db.test.while.idle: (e.g. true | false)
	 dbcp.db.test.on.return: (e.g. true | false)
	 

The Microserver annotation can also be used to set some default properties, or they can be set in an application.properties or instance.properties file ([see wiki for more details](https://github.com/aol/micro-server/wiki/Defining-Properties)).


The important properties for us to set are the datasource properties

     @Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
													 "db.connection.url","jdbc:hsqldb:mem:aname",
													"db.connection.username", "sa"})
																						     
	public class MyMainClass {
     
     
    }
