package app.jackson.com.aol.micro.server.copy;

import org.springframework.stereotype.Component;

import com.aol.micro.server.jackson.JacksonMapperConfigurator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperExtension implements JacksonMapperConfigurator {

	@Override
	public void accept(ObjectMapper t) {
		t.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
		
	}

}
