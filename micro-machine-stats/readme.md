# Machine stats plugin for Microserver

Hands-free kit for the awesame Sigar.

Uses Sigar to capture machine stats for each service (CPU load, memory usage etc). Automatically installs Sigar native libraries to ./sigar-lib.

Adds a rest end point /stats/machine to view statistics about the current container or box.

[Example micro-machine-stats apps](https://github.com/aol/micro-server/tree/master/micro-machine-stats/src/test/java/app)

(Microserver 0.78 and above!)

# To use

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-machine-stats/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.oath.microservices/micro-machine-stats)

Simply add this plugin to your classpath

Maven 
 ```xml

     <dependency>
        <groupId>com.oath.microservices</groupId>  
        <artifactId>micro-machine-stats</artifactId>
        <version>x.yz</version>
     </dependency>

```    
Gradle
```groovy
    compile 'com.oath.microservices:micro-machine-stats:x.yz'
```


## Example output
```json
{
	"cpu-stats": {
		"model": "MacBookPro",
		"mhz": 2500,
		"idle-percentage": 0.6952141057934509,
		"total-cores": 8,
		"load-average": 2.56201171875
	},
	"memory-stats": {
		"total": 17179869184,
		"actual-free": 4435292160,
		"free-percent": 25.816798210144043,
		"actual-used": 12744577024,
		"used-percent": 74.18320178985596
	},
	"swap-stats": {
		"free": 1305739264,
		"used": 4062969856,
		"total": 5368709120,
		"page-in": 40316097,
		"page-out": 312451
	}
}
```