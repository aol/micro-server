#TODOs:

Documentation with examples
Document application.properties look up
Make application.properties resource name configurable (same with instance.properties)
Finish unit test migration and example app setups
Make javax.ws.rs.Application configurable
Fix up error codes
Fix Swagger - switch back on and make configurable
Make QueryIPRetriever configurable
Make vip ip extraction header in QueryIPRetriever configurable

Make RestResources tag interface configurable per module  (to facilitate multiple embedded / colocated services)
Make MicroServerStartup a builder class so can say
		new MicroServerStartup( AppRunnerTest.class, () -> "test-app").withFilter("/*",myFilter())

 
Sample application
