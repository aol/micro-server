package com.oath.micro.server.s3.manifest.comparator;


import com.aol.micro.server.manifest.Data;
import com.aol.micro.server.manifest.VersionedKey;
import com.oath.micro.server.s3.data.S3Deleter;
import com.oath.micro.server.s3.data.S3ObjectWriter;
import com.oath.micro.server.s3.data.S3Reader;
import com.oath.micro.server.s3.data.S3StringWriter;
import com.oath.micro.server.s3.manifest.comparator.S3ManifestComparator;

import cyclops.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class S3ManifestComparatorTest {

    @Mock
    private S3Reader reader;
    @Mock
    private S3StringWriter stringWriter;
    @Mock
    private S3Deleter deleter;
    @Mock
    private S3ObjectWriter objectWriter;

    private String versionKey;
    private Date lastModTime;
    private Data<String> expectedData;

    @Before
    public void setup() {
        lastModTime = new Date();
        setupExpectedData(lastModTime, "foobar", "data", 1L);
    }

    private void setupExpectedData(Date lastModTime, String key, String data, long version) {
        expectedData = new Data<>(data, lastModTime, versionKey);
        versionKey = new VersionedKey(key, version).toJson();
        when(reader.getAsString(key)).thenReturn(Try.success(versionKey));
        when(reader.getAsObject(versionKey)).thenReturn(Try.success(expectedData));
        when(reader.getLastModified(versionKey)).thenReturn(lastModTime);
    }

    @Test
    public void loadInitial() throws Exception {
        S3ManifestComparator<String> comparator = new S3ManifestComparator<>("foobar", reader, objectWriter, deleter, stringWriter);
        comparator.load();
        assertEquals("data", comparator.getData());
        verify(reader, times(1)).getAsObject(versionKey);
    }

    @Test
    public void loadUnchanged() throws Exception {
        S3ManifestComparator<String> comparator = new S3ManifestComparator<>("foobar", reader, objectWriter, deleter, stringWriter);
        comparator.load();
        assertEquals("data", comparator.getData());
        comparator.load();
        assertEquals("data", comparator.getData());
        verify(reader, times(1)).getAsObject(versionKey);
    }

    @Test
    public void loadUpdated() throws Exception {
        S3ManifestComparator<String> comparator = new S3ManifestComparator<>("foobar", reader, objectWriter, deleter, stringWriter);
        comparator.load();
        assertEquals("data", comparator.getData());
        setupExpectedData(new Date(), "foobar", "data2", 2L);
        comparator.load();
        assertEquals("data2", comparator.getData());
    }

}