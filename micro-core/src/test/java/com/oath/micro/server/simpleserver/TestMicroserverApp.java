package com.oath.micro.server.simpleserver;


import com.oath.micro.server.MicroserverApp;
 
public class TestMicroserverApp {
 
	public static void main(String[] args) {
	//	SLF4JBridgeHandler.removeHandlersForRootLogger();
	//	SLF4JBridgeHandler.install();
		
		new MicroserverApp(()->"simple").start();
	}
 
}