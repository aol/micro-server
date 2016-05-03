package app.spring.com.aol.micro.server;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

@Getter
public class MyBean {

	public enum One{one,two};
	@Value("${one:one}")
	One one;
	@Inject
	private MyDependency injected;
}
