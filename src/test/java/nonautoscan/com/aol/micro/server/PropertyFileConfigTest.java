package nonautoscan.com.aol.micro.server;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.config.Config;
import com.aol.micro.server.spring.properties.PropertyFileConfig;
public class PropertyFileConfigTest {

	
	

	
		PropertyFileConfig config;
		@Before
		public void setUp()  {
			 config = new PropertyFileConfig();
		}

		@Test
		public void testPropertyPlaceholderConfigurer() throws IOException {
			Config.instance();
			assertThat( config.propertyPlaceholderConfigurer(),notNullValue());
		}

	

}
