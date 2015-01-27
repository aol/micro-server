package com.aol.micro.server.servers;

import lombok.Getter;

public class ServerThreadLocalVariables {

	@Getter
	private static final ThreadLocal<String> context =new ThreadLocal<String>();
	

}
