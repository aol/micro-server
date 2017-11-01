package com.oath.micro.server.application.registry;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.servers.model.ServerData;


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
		ServerData data1 = new ServerData(8080, new ArrayList<>(), null, "url", () -> "");
		ServerData data2 = new ServerData(8080, new ArrayList<>(), null, "url", () -> "");
		ServerData data3 = new ServerData(8080, new ArrayList<>(), null, "url", () -> "");

		List<ServerData> datas = Arrays.asList(data1, data2, data3);

		applicationRegister = new ApplicationRegisterImpl();

		ServerData[] dataArray = new ServerData[datas.size()];
		applicationRegister.register(datas.toArray(dataArray));
		applicationRegister.getApplication().forEach(it -> count++);
		assertThat(count, is(3));
	}
}
