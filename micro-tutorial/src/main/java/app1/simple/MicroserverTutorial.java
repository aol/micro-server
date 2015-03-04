package app1.simple;

import static com.aol.micro.server.config.Classes.HIBERNATE_CLASSES;
import static com.aol.micro.server.config.Classes.JDBC_CLASSES;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;

@Microserver(springClasses = { JDBC_CLASSES, HIBERNATE_CLASSES},  entityScan = "app1.simple")
public class MicroserverTutorial {

	public static void main(String[] args){
		
		new MicroserverApp(()->"simple").run();
	
	}
	
}
