package com.aol.micro.server.servers;

import lombok.Getter;

@Getter
public class AccessLogLocationBean {

	private final String accessLogLocation;

	public AccessLogLocationBean(String accessLogLocation) {
		this.accessLogLocation = accessLogLocation;
	}

}
