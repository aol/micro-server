 # HikariCP plugin

[micro-hikaricp example apps](https://github.com/aol/micro-server/tree/master/micro-hikaricp/src/test/java/app)

Creates a DataSource Spring Bean with name "mainDataSource". This will be based on [HikariCP](http://brettwooldridge.github.io/HikariCP/ludicrous.html).

## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-hikaricp/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-hikaricp)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-hikaricp</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.oath.microservices:micro-hikaricp:x.yz'

# Configuring a data source

A datasource can be configured by setting the following properties in application.properties, instance.properties or via the Microserver annotation

     db.connection.driver: (e.g. (org.hsqldb.jdbcDriver)
	 db.connection.url: (e.g. jdbc:hsqldb:mem:aname)
	 db.connection.username: (e.g. admin)
	 db.connection.password: (e.g. password)
	 db.connection.dialect:  (e.g. org.hibernate.dialect.HSQLDialect)
	 db.connection.hibernate.showsql: (e.g. true | false)
	 db.connection.ddl.auto: (e.g. create-drop)
	 hikaricp.db.connection.max.pool.size (e.g. 30)
	 hikaricp.db.connection.min.idle (e.g. 2)
	 hikaricp.db.connection.idle.timeout (e.g. 1800000)

The Microserver annotation can also be used to set some default properties, or they can be set in an application.properties or instance.properties file ([see wiki for more details](https://github.com/aol/micro-server/wiki/Defining-Properties)).


The important properties for us to set are the datasource properties

     @Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
													 "db.connection.url","jdbc:hsqldb:mem:aname",
													"db.connection.username", "sa"})
																						     
	public class MyMainClass {
     
     
    }
