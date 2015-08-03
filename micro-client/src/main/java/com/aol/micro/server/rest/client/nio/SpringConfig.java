package com.aol.micro.server.rest.client.nio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;


@Configuration
public class SpringConfig {
	@Bean
	public NIORestClient restClient(){
		return new NIORestClient(new AsyncRestTemplate(new HttpComponentsAsyncClientHttpRequestFactory()));
	}
}
