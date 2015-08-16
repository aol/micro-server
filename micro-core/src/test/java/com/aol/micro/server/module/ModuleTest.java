package com.aol.micro.server.module;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.aol.cyclops.lambda.monads.SequenceM;
import com.aol.micro.server.Plugin;
public class ModuleTest {

	@Test
	public void testProviders(){
		//test MyPlugin working
		assertThat("com.aol.micro.server.rest.providers;com.my.new.provider,com.my.new.provider2",
				equalTo(ConfigurableModule.builder().build().getProviders()));
		System.out.println(new ModuleImpl().getProviders());
		String additional = SequenceM
				.fromStream(
						Arrays.asList(new MyPlugin())
								.stream()).filter(module -> module.providers()!=null)
								.flatMapCollection(Plugin::providers)
								.join(";");
		
		assertThat(additional, equalTo("com.my.new.provider;com.my.new.provider2"));
	}
	@Test(expected=IncorrectJaxRsPluginsException.class)
	public void testJaxWsRsApplication(){
		//test MyPlugin working
		assertThat("com.aol.micro.server.rest.providers,com.my.new.provider,com.my.new.provider2",
				equalTo(ConfigurableModule.builder().build().getJaxWsRsApplication()));
	
		
	}
	
	static class ModuleImpl  implements Module{
		public String getContext(){
			return "hello world";
		}
		public String getJaxWsRsApplication(){
			return null;
		}
	}
}
