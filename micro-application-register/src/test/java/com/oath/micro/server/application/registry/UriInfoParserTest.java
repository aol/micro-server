package com.oath.micro.server.application.registry;

import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.*;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UriInfoParserTest {

    @Test
    public void toRegisterEntryFromQueryParameters() throws Exception {
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        MultivaluedMap<String, String> data = new MultivaluedHashMap<>();
        data.put("port", Arrays.asList("8080"));
        data.put("externalPort", Arrays.asList("9090"));
        data.put("hostname", Arrays.asList("host1"));
        data.put("module", Arrays.asList("module1"));
        data.put("context", Arrays.asList("context1"));
        data.put("target", Arrays.asList("target1"));
        data.put("health", Arrays.asList("OK"));
        data.put("manifest.Implementation-revision", Arrays.asList("revision1"));
        data.put("manifest.Implementation-Timestamp", Arrays.asList("2017_001"));
        data.put("manifest.Implementation-Version", Arrays.asList("v1"));

        when(uriInfo.getQueryParameters()).thenReturn(data);

        Optional<RegisterEntry> reOptional = UriInfoParser.toRegisterEntry(uriInfo);
        assertTrue(reOptional.isPresent());

        RegisterEntry re = reOptional.get();
        assertThat(re.getPort(), is(8080));
        assertThat(re.getExternalPort(), is(9090));
        assertThat(re.getHostname(), is("host1"));
        assertThat(re.getModule(), is("module1"));
        assertThat(re.getContext(), is("context1"));
        assertThat(re.getTarget(), is("target1"));
        assertThat(re.getHealth(), is(Health.OK));
        assertThat(re.getManifest().get("Implementation-revision"), is("revision1"));
        assertThat(re.getManifest().get("Implementation-Timestamp"), is("2017_001"));
        assertThat(re.getManifest().get("Implementation-Version"), is("v1"));
    }

    @Test
    public void toRegisterEntryFromEmptyQueryParameter() throws Exception {
        UriInfo uriInfo = Mockito.mock(UriInfo.class);
        when(uriInfo.getQueryParameters()).thenReturn(new MultivaluedHashMap<>());

        Optional<RegisterEntry> re = UriInfoParser.toRegisterEntry(uriInfo);
        assertFalse(re.isPresent());
    }
}
