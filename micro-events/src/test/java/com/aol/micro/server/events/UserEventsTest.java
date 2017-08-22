package com.aol.micro.server.events;

import org.junit.Before;
import org.junit.Test;

import com.aol.micro.server.events.RequestTypes.AddLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RemoveLabelledQuery;
import com.aol.micro.server.events.RequestTypes.RequestData;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class UserEventsTest {

    String query;
    long corrId;
    String user;
    String addData;

    @Before
    public void setUp() {
        query = "query as string";
        corrId = 1234;
        user = "user";
        addData = "additional data";
    }

    @Test
    public void createAddLabelledQuery() {

        AddLabelledQuery<String> userQuery = UserEvents.start(query, corrId, user);
        RequestData rd = userQuery.getData();

        assertThat(rd.getQuery(), is(query));
        assertThat(rd.getType(), is(user));
        assertThat(rd.getCorrelationId(), is(corrId));
    }

    @Test
    public void createAddLabelledQueryWithAdditionalData() {

        AddLabelledQuery<String> userQuery = UserEvents.start(query, corrId, user, addData);
        RequestData rd = userQuery.getData();

        assertThat(rd.getQuery(), is(query));
        assertThat(rd.getType(), is(user));
        assertThat(rd.getCorrelationId(), is(corrId));
        assertThat(rd.getAdditionalData(), is(addData));
    }

    @Test
    public void createRemoveLabelledQuery() {

        RemoveLabelledQuery<String> userQuery = UserEvents.finish(query, corrId, user);
        RequestData<String> rd = userQuery.getData();

        assertThat(rd.getQuery(), is(query));
        assertThat(rd.getType(), is(user));
        assertThat(rd.getCorrelationId(), is(corrId));
    }
}
