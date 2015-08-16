package java8.meetup;

import com.aol.micro.server.MicroserverApp;

public class Meetup {

	public static void main(String[] args){
		new MicroserverApp(()->"meetup").start();
	}
	
}
