package app1.simple;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;

@Microserver(entityScan = "app1.simple", propertiesName="tutorial.properties")
public class MicroserverTutorial {

	public static void main(String[] args){
		
		new MicroserverApp(()->"simple").run();
	
	}
	
}
