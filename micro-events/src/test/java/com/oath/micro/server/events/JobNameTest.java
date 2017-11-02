package com.oath.micro.server.events;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.oath.micro.server.events.JobName.Types;

public class JobNameTest {
    JobName simple = Types.SIMPLE.getCreator();
    JobName full = Types.FULL.getCreator();
    JobName one = Types.PACKAGE.getCreator();

    @Test
    public void testSimple() {
        assertThat(simple.getType(JobNameTest.class), equalTo("JobNameTest"));
    }

    @Test
    public void testFull() {
        assertThat(full.getType(JobNameTest.class), equalTo("com.oath.micro.server.events.JobNameTest"));
    }

    @Test
    public void testPackage() {
        assertThat(one.getType(JobNameTest.class), equalTo("events.JobNameTest"));
    }

}
