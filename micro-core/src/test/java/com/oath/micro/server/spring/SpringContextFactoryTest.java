package com.oath.micro.server.spring;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.oath.micro.server.config.Config;
import com.oath.micro.server.config.Microserver;

/**
 * Any new classes shoud be added into blackListedClasses for test to pass until proper solution with
 * matching varargs will be found
 */

@Microserver(blacklistedClasses = {Integer.class, SpringContextFactoryTest.class})
public class SpringContextFactoryTest {


	
	@Test
	public void blacklisting() {
						
		SpringBuilder springBuilder = mock(SpringBuilder.class);
		
		Set<Class<?>> classes = new HashSet<>();
		classes.add(Integer.class);
		classes.add(String.class);
		Config config = Config.instance().withBasePackages(new String[] {"com.aol.micro.server.spring"}).set();
		new SpringContextFactory(config ,this.getClass(), classes).withSpringBuilder(springBuilder).createSpringContext();
	    ArgumentCaptor<Class> varArgs = ArgumentCaptor.forClass(Class.class);
		verify(springBuilder).createSpringApp(anyObject(), varArgs.capture());
		Assert.assertTrue(varArgs.getAllValues().contains(String.class));
		Assert.assertFalse(varArgs.getAllValues().contains(Integer.class));

	}
	
}
