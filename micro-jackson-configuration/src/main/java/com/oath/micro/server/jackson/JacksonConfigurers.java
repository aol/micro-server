package com.oath.micro.server.jackson;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.rest.jackson.JacksonUtil;

@Component
public class JacksonConfigurers {

	private final List<JacksonMapperConfigurator> configureMapper;

	@Autowired
	public JacksonConfigurers(List<JacksonMapperConfigurator> configureMapper) {
		
		this.configureMapper = configureMapper;
	}
	
	@PostConstruct
	public void init(){
		JacksonUtil.setJacksonConfigurers(configureMapper);
	}
	
}

