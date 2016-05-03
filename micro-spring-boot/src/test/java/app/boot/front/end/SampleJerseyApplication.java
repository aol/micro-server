package app.boot.front.end;

import com.aol.micro.server.MicroserverApp;
import com.aol.micro.server.config.Microserver;
import com.aol.micro.server.spring.boot.MicroSpringBoot;

@Microserver
@MicroSpringBoot
public class SampleJerseyApplication  {

	public static void main(String[] args) {
		
		MicroserverApp app = new MicroserverApp(()->"hello");	
		
	}

}
