package com.aol.micro.server.events;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.events.RequestTypes.AddLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RemoveLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RequestData;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class LabelledEventsTest {

    String query;
    long corrId;
    String label;
    String addData;

    @Before
    public void setUp() {
        query = "query as string";
        corrId = 1234;
        label = "label";
        addData = "additional data";
    }

    @Test
    public void createAddLabelledQuery() {

        AddLabelledQuery<String> userQuery = LabelledEvents.start(query, corrId, label);
        RequestData rd = userQuery.getData();

        assertThat(rd.getQuery(), is(query));
        assertThat(rd.getType(), is(label));
        assertThat(rd.getCorrelationId(), is(corrId));
    }

    @Test
    public void createAddLabelledQueryWithAdditionalData() {

        AddLabelledQuery<String> userQuery = LabelledEvents.start(query, corrId, label, addData);
        RequestData rd = userQuery.getData();

        assertThat(rd.getQuery(), is(query));
        assertThat(rd.getType(), is(label));
        assertThat(rd.getCorrelationId(), is(corrId));
        assertThat(rd.getAdditionalData(), is(addData));
    }

    @Test
    public void createRemoveLabelledQuery() {

        RemoveLabelledQuery<String> userQuery = LabelledEvents.finish(query, corrId, label);
        RequestData<String> rd = userQuery.getData();

        assertThat(rd.getQuery(), is(query));
        assertThat(rd.getType(), is(label));
        assertThat(rd.getCorrelationId(), is(corrId));
    }
}
