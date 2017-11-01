# Dropwizard metrics plugin for Microserver

[micro-metrics example apps](https://github.com/aol/micro-server/tree/master/micro-metrics/src/test/java/app/metrics)

This adds support for Dropwizard metrics annotations on Spring beans. For more detailed info see [Metrics Spring](http://www.ryantenney.com/metrics-spring/)

## To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-metrics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.aol.microservices/micro-metrics)

Simply add to the classpath

Maven 

     <dependency>
        <groupId>com.aol.microservices</groupId>  
        <artifactId>micro-metrics</artifactId>
        <version>x.yz</version>
     </dependency>
     
Gradle

    compile 'com.aol.microservices:micro-metrics:x.yz'
    
 # Configuring Metrics Reporters
 
 By default we report to the console hourly and to JMX. To configure other reporters, this can be done via the setInit method on the CodahaleMetricsConfigurer class e.g.
 
    CodahaleMetricsConfigurer.setInit( metricRegistry -> 
              TestReporter.forRegistry(metricRegistry)
		         		  .build()
		         		  .start(10, TimeUnit.MILLISECONDS));
		         		  
		         		  
# An example Spring Bean capturing Metrics


       
	@Component
	public class TimedResource {
	
		
		@Timed
		public String times(){
	
			return "ok!";
		}
	}
       
		         		  