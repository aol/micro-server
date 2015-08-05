package com.aol.micro.server.application.registry;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.servers.model.ServerData;
import com.google.common.collect.Lists;

public class ApplicationRegisterTest {

	private ApplicationRegisterImpl applicationRegister;
	private int count;

	@Before
	public void setUp() {
		count = 0;
	}

	@Test
	public void testConstructor() {
		applicationRegister = new ApplicationRegisterImpl();
		
		assertThat(applicationRegister.getApplication(), is(nullValue()));
	}

	@Test
	public void testRegister() {
		ServerData data1 = new ServerData(8080, Lists.newArrayList(), null, "url", () -> "");
		ServerData data2 = new ServerData(8080, Lists.newArrayList(), null, "url", () -> "");
		ServerData data3 = new ServerData(8080, Lists.newArrayList(), null, "url", () -> "");

		List<ServerData> datas = Lists.newArrayList(data1, data2, data3);

		applicationRegister = new ApplicationRegisterImpl();

		ServerData[] dataArray = new ServerData[datas.size()];
		applicationRegister.register(datas.toArray(dataArray));
		applicationRegister.getApplication().forEach(it -> count++);
		assertThat(count, is(3));
	}
}
