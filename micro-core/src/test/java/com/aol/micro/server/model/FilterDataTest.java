package com.aol.micro.server.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.servlet.Filter;

import org.junit.Test;

import com.aol.micro.server.servers.model.FilterData;

public class FilterDataTest {

	@Test
	public void testFilterData() {
		Filter filter = mock(Filter.class);
		FilterData data = new FilterData("mapping", "filterName", filter);
		assertThat(data.getMapping(), is("mapping"));
		assertThat(data.getFilterName(), is("filterName"));
		assertThat(data.getFilter(), is(filter));
	}

}
