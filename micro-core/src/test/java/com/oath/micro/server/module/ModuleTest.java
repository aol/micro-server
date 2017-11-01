package com.oath.micro.server.module;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import cyclops.reactive.ReactiveSeq;
import org.junit.Test;


import com.oath.micro.server.Plugin;
public class ModuleTest {

	@Test
	public void testProviders(){
		//test MyPlugin working
		assertThat("com.oath.micro.server.rest.providers,com.my.new.provider,com.my.new.provider2",
				equalTo(ConfigurableModule.builder().build().getProviders()));
		System.out.println(new ModuleImpl().getProviders());
		String additional = ReactiveSeq
				.fromStream(
						Arrays.asList(new MyPlugin())
								.stream()).filter(module -> module.providers()!=null)
								.flatMapI(Plugin::providers)
								.join(",");
		
		assertThat(additional, equalTo("com.my.new.provider,com.my.new.provider2"));
	}
	
	static class ModuleImpl  implements Module{
		public String getContext(){
			return "hello world";
		}
	}
}
