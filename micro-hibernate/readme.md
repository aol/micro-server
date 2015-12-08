 # Hibernate & Spring Data plugin
 
 [micro-hibernate example apps](https://github.com/aol/micro-server/tree/master/micro-hibernate/src/test/java/app)

Adds Spring Data, Spring JDBC and Hibernate support. 
Creates a DataSource Spring Bean with name "mainDataSource" from v0.63 this will be based on [HikariCP](http://brettwooldridge.github.io/HikariCP/ludicrous.html).

## To use


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-hibernate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-hibernate)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-data</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-data:x.yz'

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
													"db.connection.username", "sa",
													"db.connection.dialect","org.hibernate.dialect.HSQLDialect",
													"db.connection.ddl.auto","create-drop"})
																						     
	public class MyMainClass {
     
     
    }


# Hibernate

[Hibernate example app](https://github.com/aol/micro-server/tree/master/micro-data/src/test/java/app/hibernate/com/aol/micro/server)

Use the Microserver annotation to set the Hibernate entityScan

    @Microserver(entityScan="app.hibernate.com.aol.micro.server")
    public class MyMainClass {
     
     
    }
 

 ## SessionFactory 
 
 micro-data will create a Spring managed SessionFactory for you that you can inject into your Spring beans to take full advantage of Hibernate functionality.
 
 E.g. 
 
    @Rest
    @Path("/persistence")
    public class PersistentResource{

	
	    private final SessionFactory sessionFactory;
	
	
	    @Autowired
	    public PersistentResource(SessionFactory sessionFactory) {
	
		    this.sessionFactory = sessionFactory;
	    }
	
	    @GET
	    @Produces("text/plain")
	    @Path("/create")
	    public String createEntity() {
		
		    final Session session = sessionFactory.openSession();    
		    session.save(HibernateEntity.builder()
								        .name("test")
								        .value("value").build());
		    session.flush();
		    return "ok";
	    }
	
	    @GET
	    @Produces("application/json")
	    @Path("/get")
	    public List<HibernateEntity> get(){
	    	final Session session = sessionFactory.openSession();
		
		    Criteria criteria = session.createCriteria(HibernateEntity.class)
								        .add(Example.create(HibernateEntity.builder()
										.name("test")
										.build()));
		
		    return criteria.list();
		
	    }
	
    }
    
 # Spring Data
 
 [Spring Data example app](https://github.com/aol/micro-server/tree/master/micro-data/src/test/java/app/spring/data/jpa/com/aol/micro/server)
 
 On your main class add the following annotation to switch on Spring Data
 
     @EnableJpaRepositories
     
 Example : note in this example we are also going to use Hibernate to construct an in-memory database
 
 
      @Microserver(properties={"db.connection.driver","org.hsqldb.jdbcDriver",
	    "db.connection.url","jdbc:hsqldb:mem:aname",
	    "db.connection.username", "sa",
	    "db.connection.dialect","org.hibernate.dialect.HSQLDialect",
	    "db.connection.ddl.auto","create-drop"}, entityScan="app.spring.data.jpa.com.aol.micro.server")
      @EnableJpaRepositories
      public class SpringDataTest {

      private final AsyncRestClient<List<SpringDataEntity>> listClient = new       AsyncRestClient(1000,1000).withGenericResponse(List.class, SpringDataEntity.class);

	RestAgent rest = new RestAgent();
	
	MicroserverApp server;
	
	@Before
	public void startServer(){
		
		Config.reset();
		server = new MicroserverApp(()->"hibernate-app");
		server.start();

	}
	
	@After
	public void stopServer(){
		server.stop();
	}
	
	@Test  
	public void runAppAndBasicTest() throws InterruptedException, ExecutionException{
		
		
		
		assertThat(rest.get("http://localhost:8080/hibernate-app/persistence/create"),is("ok"));
		assertThat(listClient.get("http://localhost:8080/hibernate-app/persistence/get").get().get(0),is(SpringDataEntity.class));
		
		
	}
	
  }

## Spring Data Repositories 

We can define a Spring Data Repository e.g. A Crud Repository for a simple Entity

	public interface SpringDataRepository extends CrudRepository<SpringDataEntity, Long> {
	
	}

## Using Spring Data Repositories 

Simply inject the Repository into your Rest Resource

    @Rest
    @Path("/persistence")
    public class PersistentResource  {

	
	  private final SpringDataRepository dao;
	
	  @Autowired
	  public PersistentResource(SpringDataRepository dao) {
	
		  this.dao = dao;
	 }
	
	  @GET
	  @Produces("text/plain")
	  @Path("/create")
	  public String createEntity() {
		
		 SpringDataEntity saved =	dao.save(SpringDataEntity.builder()
								.name("test")
								.value("value").build());
		
		  return "ok";
	 }
	 
	  @GET
	  @Produces("application/json")
	  @Path("/get")
	  public Iterable<SpringDataEntity> get(){
		
		 return dao.findAll();

	  }
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
