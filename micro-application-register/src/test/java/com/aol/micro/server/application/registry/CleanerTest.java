package com.aol.micro.server.application.registry;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CleanerTest {

    Cleaner cleaner;
    Register writer;
    RegisterEntry entry;
    Finder finder;
    RegisterConfig registerConfig;

    @Before
    public void setUp() throws Exception {
        try {
            new File(
                     System.getProperty("java.io.tmpdir"), "lana-service-reg-cleaner").delete();
        } catch (Exception e) {
        }

        new File(
                 System.getProperty("java.io.tmpdir"), "lana-service-reg-cleaner").mkdirs();
        registerConfig = new RegisterConfig(
                                            new File(
                                                     System.getProperty("java.io.tmpdir"),
                                                     "lana-service-reg-cleaner").getAbsolutePath());
        writer = new Register(
                              registerConfig);
        finder = new Finder(
                            registerConfig);
        cleaner = new Cleaner(
                              registerConfig, 1);

        entry = new RegisterEntry(
                                  8080, "host", "module", "context", new Date(), null, 8080);

    }

    @Test
    public void testClean() {
        writer.register(entry.withTime(new Date(
                                                System.currentTimeMillis() - 2000)));

        cleaner.clean();
        List<RegisterEntry> list = finder.find();
        assertThat(list.size(), equalTo(0));
    }
}
