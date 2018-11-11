package com.oath.micro.server.machine.stats.sigar;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class StatsServletContextListenerTest {
  @Before
  public void setUp() {
    sce = Mockito.mock(ServletContextEvent.class);
    servletContext = Mockito.mock(ServletContext.class);
    contextListener = new StatsServletContextListener();
  }

  @Test
  public void testGrizzly() {
    contextListener.contextInitialized(sce);
    Assert.assertThat(System.getProperty("java.library.path"), Matchers.is(System.getProperty("user.dir") + "/sigar-lib"));
    contextListener.contextDestroyed(sce);
  }

  private StatsServletContextListener contextListener;
  private ServletContextEvent sce;
  private ServletContext servletContext;
}
