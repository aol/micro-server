package nonautoscan.com.oath.micro.server;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.config.Config;
import com.oath.micro.server.spring.properties.PropertyFileConfig;
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
