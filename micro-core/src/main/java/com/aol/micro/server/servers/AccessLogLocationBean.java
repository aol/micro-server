package com.aol.micro.server.servers;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AccessLogLocationBean {

	private final String accessLogLocation;

	@Autowired
	public AccessLogLocationBean(@Value("${access.log.output:./logs/}") String accessLogLocation) {
		this.accessLogLocation = accessLogLocation;
	}

}
