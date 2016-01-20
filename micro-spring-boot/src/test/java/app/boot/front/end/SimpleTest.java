package app.boot.front.end;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.aol.micro.server.testing.RestAgent;


public class SimpleTest {
	@Value("${local.server.port}")
	private int port=8080;
	RestAgent rest = new RestAgent();
	@Test
	public void ping(){
		SampleJerseyApplication.main(null);
		assertThat(rest.get("http://localhost:8080/hello/status/ping"),is("ok"));
	}
}
