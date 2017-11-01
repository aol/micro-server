package com.aol.micro.server.application.registry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class WriterTest {

    Register writer;
    RegisterEntry entry;

    @Before
    public void setUp() throws Exception {
        try {
            FileUtils.deleteDirectory(new File(
                                               System.getProperty("java.io.tmpdir"), "service-reg-writer"));
        } catch (Exception e) {
        }

        new File(
                 System.getProperty("java.io.tmpdir"), "service-reg-writer").mkdirs();
        writer = new Register(
                              new RegisterConfig(
                                                 new File(
                                                          System.getProperty("java.io.tmpdir"),
                                                          "service-reg-writer").getAbsolutePath()));

        entry = new RegisterEntry(
                                  8080, "host", "module", "context", new Date(), null, 8080);
    }

    @Test
    public void testRegister() {
        writer.register(entry);
        File dir = new File(
                            new File(
                                     System.getProperty("java.io.tmpdir"), "service-reg-writer"),
                            "module");
        assertThat(dir.listFiles().length, equalTo(1));
    }
}
