package com.oath.micro.server.application.registry;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.oath.micro.server.rest.jackson.JacksonUtil;

public class RegisterEntryTest {

    RegisterEntry entry;

    @Before
    public void setUp() throws Exception {
        entry = RegisterEntry.builder()
            .port(8080)
            .hostname("host")
            .module("module")
            .context("context")
            .time(new Date())
            .uuid("1")
            .target("target")
            .externalPort(9090)
            .build();
        Map<String, String> manifest = entry.getManifest();
        manifest.put("Implementation-revision", "a2edfe4bc");
        manifest.put("Implementation-Version", "version");
        manifest.put("Implementation-Timestamp", "2017_1201");
    }

    @Test
    public void test() {
        assertTrue(JacksonUtil.serializeToJson(entry).contains("\"context\":\"context"));
    }

    @Test
    public void matches() throws Exception {
        RegisterEntry re = new RegisterEntry();
        re.getManifest().clear();
        assertFalse(entry.matches(re));

        re = RegisterEntry.builder().port(8080).externalPort(-1).build();
        re.getManifest().clear();
        assertTrue(entry.matches(re));

        re = RegisterEntry.builder().port(8080).externalPort(9090).build();
        re.getManifest().clear();
        assertTrue(entry.matches(re));

        re = RegisterEntry.builder().port(8080).hostname("host").externalPort(9090).build();
        re.getManifest().clear();
        assertTrue(entry.matches(re));

        re = RegisterEntry.builder().port(8080).hostname("host1").externalPort(9090).build();
        re.getManifest().clear();
        assertFalse(entry.matches(re));

        re = RegisterEntry.builder().port(8080).hostname("host").externalPort(9090).build();
        re.getManifest().clear();
        re.getManifest().put("Implementation-revision", "a2edfe4bc");
        assertTrue(entry.matches(re));

        re = RegisterEntry.builder().port(8080).hostname("host").externalPort(9090).build();
        re.getManifest().clear();
        re.getManifest().put("Implementation-Version", "version1");
        assertFalse(entry.matches(re));
    }
}
