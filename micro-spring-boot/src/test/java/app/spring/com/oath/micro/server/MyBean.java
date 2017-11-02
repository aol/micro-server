package app.spring.com.oath.micro.server;

import javax.inject.Inject;

import lombok.Getter;

@Getter
public class MyBean {

	@Inject
	private MyDependency injected;
}
