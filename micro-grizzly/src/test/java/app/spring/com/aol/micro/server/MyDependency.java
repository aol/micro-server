package app.spring.com.aol.micro.server;

import lombok.Getter;

import org.springframework.stereotype.Component;

@Component
@Getter
public class MyDependency {

	private String data = "hello world";
}
