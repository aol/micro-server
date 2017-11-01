package app.spring.com.oath.micro.server;

import lombok.Getter;

import org.springframework.stereotype.Component;

@Component
@Getter
public class MyDependency {

	private String data = "hello world";
}
