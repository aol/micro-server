package nonautoscan.com.aol.micro.server;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.spring.properties.PropertyFileConfig;
public class PropertyFileConfigTest {

	
	

	
		PropertyFileConfig config;
		@Before
		public void setUp()  {
			 config = new PropertyFileConfig();
		}

		@Test
		public void testPropertyPlaceholderConfigurer() throws IOException {
			assertThat( config.propertyPlaceholderConfigurer(),notNullValue());
		}

	

}
