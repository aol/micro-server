# JDBC plugin

[micro-jdbc example apps](https://github.com/aol/micro-server/tree/master/micro-dbcp/src/test/java/app/pure/jdbc/com/aol/micro/server)

Adds Spring JDBC. 
This plugin needs either micro-hikaricp or micro-dbcp2 plugin at runtime to provide a DataSource (bean name for qualify / inject purposes is mainDataSource)

## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-data/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-jdbc)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-jdbc</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.oath.microservices:micro-jdbc:x.yz'

# Configuring a data source

A datasource can be configured by setting the following properties in application.properties, instance.properties or via the Microserver annotation

     db.connection.driver: (e.g. (org.hsqldb.jdbcDriver)
	 db.connection.url: (e.g. jdbc:hsqldb:mem:aname)
	 db.connection.username: (e.g. admin)
	 db.connection.password: (e.g. password)
	 db.connection.dialect:  (e.g. org.hibernate.dialect.HSQLDialect)
	 db.connection.hibernate.showsql: (e.g. true | false)
	 db.connection.ddl.auto: (e.g. create-drop)

The Microserver annotation can also be used to set some default properties, or they can be set in an application.properties or instance.properties file ([see wiki for more details](https://github.com/aol/micro-server/wiki/Defining-Properties)).


The important properties for us to set are the datasource properties

     @Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
													 "db.connection.url","jdbc:hsqldb:mem:aname",
													"db.connection.username", "sa"})
																						     
	public class MyMainClass {
     
     
    }



 # Plain ol' JDBC
 
[JDBC Template Exmaple App](https://github.com/aol/micro-server/tree/master/micro-data/src/test/java/app/jdbc/com/aol/micro/server)

The micro-data plugin also facilitate JDBC use via the Spring JDBCTemplate. A Spring called SQL will be created that contains a JDBCTemplate, simply inject SQL into your classes.
 
 e.g.
 
    @Rest
    @Path("/persistence")
    public class PersistentResource  {

	    private final SQL dao;

	    @Autowired
	    public PersistentResource(SQL dao) {

	    	this.dao = dao;
    	}

    	@GET    
    	@Produces("text/plain")
    	@Path("/create")
    	public String createEntity() {
    		dao.getJdbc().update("insert into t_jdbc VALUES (1,'hello','world',1)");

    		return "ok";
    	}

    	@GET
    	@Produces("application/json")    
    	@Path("/get")
	    public JdbcEntity get() {
	    	return dao.getJdbc().<JdbcEntity> queryForObject("select * from t_jdbc", new BeanPropertyRowMapper(JdbcEntity.class));
	    }

    }
