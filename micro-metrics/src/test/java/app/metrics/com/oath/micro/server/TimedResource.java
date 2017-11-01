package app.metrics.com.oath.micro.server;

import org.springframework.stereotype.Component;

import com.codahale.metrics.annotation.Timed;

@Component
public class TimedResource {

	
	@Timed
	public String times(){

		return "ok!";
	}
}
