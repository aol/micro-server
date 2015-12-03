package app.metrics.boot.com.aol.micro.server;

import org.springframework.stereotype.Component;

import com.codahale.metrics.annotation.Timed;
import com.ryantenney.metrics.annotation.Counted;

@Component
public class TimedResource {

	
	@Timed
	public String times(){

		return "ok!";
	}
}
