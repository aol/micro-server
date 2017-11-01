package com.oath.micro.server.machine.stats.sigar;

import static org.hamcrest.Matchers.is
import static org.junit.Assert.assertThat
import static org.mockito.Mockito.mock

import javax.servlet.ServletContext
import javax.servlet.ServletContextEvent

import org.junit.Before
import org.junit.Test


class StatsServletContextListenerTest {

	private StatsServletContextListener contextListener

	private ServletContextEvent sce

	private ServletContext servletContext

	@Before
	public void setUp() {
		sce = mock(ServletContextEvent.class)
		servletContext = mock(ServletContext.class)
		contextListener = new StatsServletContextListener()
	}

	@Test
	public void testGrizzly() {
		contextListener.contextInitialized(sce)
		assertThat(System.getProperty("java.library.path"), is(System.getProperty("user.dir") + "/sigar-lib"))
		contextListener.contextDestroyed(sce)
	}
}
