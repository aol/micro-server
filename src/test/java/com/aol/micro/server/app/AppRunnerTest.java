package com.aol.micro.server.app;


import com.aol.micro.server.GrizzlyServerStartup;
import com.google.common.collect.Lists;

public class AppRunnerTest {
	

		@SuppressWarnings("unchecked")
		public static void main(String[] args) throws InterruptedException {
			new GrizzlyServerStartup( () -> "test-app")
					.start();
		}

}
