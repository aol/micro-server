package com.oath.micro.server.rest.client.nio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;


@Configuration
public class SpringConfig {
	
	@Value("${nio.rest.connection.request.timeout:10000}")
	private int connectionRequestTimeout;
	
	
	@Value("${nio.rest.connection.read.timeout:0}")
	private int readTimeout;


	@Value("${nio.rest.connection.connect.timeout:2000}")
	private int connectTimeout;
	
	@Bean
	public NIORestClient restClient(){
		HttpComponentsAsyncClientHttpRequestFactory rest = new HttpComponentsAsyncClientHttpRequestFactory();
		rest.setConnectionRequestTimeout(connectionRequestTimeout);
		rest.setReadTimeout(readTimeout);
		rest.setConnectTimeout(connectTimeout);
		return new NIORestClient(new AsyncRestTemplate(rest));
	}
}
