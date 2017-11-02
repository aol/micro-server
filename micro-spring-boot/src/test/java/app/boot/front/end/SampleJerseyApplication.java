package app.boot.front.end;

import com.oath.micro.server.MicroserverApp;
import com.oath.micro.server.config.Microserver;
import com.oath.micro.server.spring.boot.MicroSpringBoot;

@Microserver
@MicroSpringBoot
public class SampleJerseyApplication  {

	public static void main(String[] args) {
		
		MicroserverApp app = new MicroserverApp(()->"hello");	
		
	}

}
