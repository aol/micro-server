package com.aol.micro.server.client;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.aol.cyclops.control.ReactiveSeq;
import com.aol.micro.server.Plugin;
import com.aol.micro.server.module.ConfigurableModule;
import com.aol.micro.server.module.Module;

public class ClientModuleTest {
	@Test
	public void testProviders(){
		System.out.println(ConfigurableModule.builder().build().getProviders());
		//test MyPlugin working
		
		System.out.println(new ModuleImpl().getProviders());
		String additional = ReactiveSeq
				.fromStream(
						Arrays.<Plugin>asList(new TestPlugin())
								.stream()).filter(module -> module.providers()!=null)
								.flatMapIterable(Plugin::providers)
								.join(",");
		
		assertThat(additional, equalTo(""));
	}
	static class ModuleImpl  implements Module{
		public String getContext(){
			return "hello world";
		}
	}
}
