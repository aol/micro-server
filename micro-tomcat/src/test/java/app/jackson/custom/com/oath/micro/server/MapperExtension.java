package app.jackson.custom.com.oath.micro.server;

import org.springframework.stereotype.Component;

import com.oath.micro.server.jackson.JacksonMapperConfigurator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperExtension implements JacksonMapperConfigurator {

	@Override
	public void accept(ObjectMapper t) {
		t.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		
	}

}
