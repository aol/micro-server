package com.aol.micro.server.rest;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import cyclops.collections.immutable.LinkedListX;
import cyclops.data.Seq;
import org.junit.Test;



import com.aol.micro.server.rest.jackson.JacksonUtil;

import lombok.Getter;
import lombok.Setter;

public class JacksonUtilTest {

    @Test
    public void generateSampleRequest() {

        DummyQueryRequest request = new DummyQueryRequest();
        request.getData()
               .add("blah");

        assertTrue(JacksonUtil.serializeToJson(request)
                              .contains("strData"));

    }

    @Test
    public void serialiseAndDeserialise() {
        DummyQueryRequest request = new DummyQueryRequest();
        request.getData()
               .add("blah");
        String requestStr = (String) JacksonUtil.serializeToJsonLogFailure(request);
        DummyQueryRequest requestDeserialised = JacksonUtil.convertFromJson(requestStr, DummyQueryRequest.class);
        assertTrue(request.getData()
                          .contains("blah"));
    }

    @Test
    public void serializeToPersistentList() {

        LinkedListX<Integer> list = LinkedListX.of(1, 2, 3, 4);
        String jsonString = JacksonUtil.serializeToJson(list);

        Seq<Integer> stack = JacksonUtil.convertFromJson(jsonString, Seq.class);

        assertThat(stack, equalTo(list.reverse()));
    }

}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queryRequest")
@XmlType(name = "")
class DummyQueryRequest {

    @XmlElement(name = "strData")
    @Getter
    @Setter
    private List<String> data = new ArrayList();

}
