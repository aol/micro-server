package nonautoscan.com.aol.micro.server;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.spring.datasource.JdbcConfig;

public class JdbcConfigTest {

	JdbcConfig config;
	@Before
	public void setUp() throws Exception {
		config = new JdbcConfig("driverClassName", "url", "username", "password", "showSql", "mysql", "none",null,null);
	}

	@Test
	public void test() {
		assertThat (config, notNullValue());
		assertThat(config.getDdlAuto(),is("none"));
		assertThat(config.getDialect(),is("mysql"));
		assertThat(config.getDriverClassName(),is("driverClassName"));
		assertThat(config.getUrl(),is("url"));
		assertThat(config.getUsername(),is("username"));
		assertThat(config.getPassword(),is("password"));
		assertThat(config.getShowSql(),is("showSql"));
	}
}
