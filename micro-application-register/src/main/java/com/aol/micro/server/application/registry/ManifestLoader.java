package com.aol.micro.server.application.registry;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import cyclops.function.FluentFunctions;
import cyclops.stream.ReactiveSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ManifestLoader {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public final static ManifestLoader instance = new ManifestLoader();
    Supplier<Map<String, String>> fn = FluentFunctions.of(this::manifest)
                                                      .memoize();

    public Map<String, String> getManifest() {
        return fn.get();
    }

    private Map<String, String> manifest() {

        try {
            return ReactiveSeq.of("META-INF/MANIFEST.MF")
                              .map(url -> this.getClass()
                                              .getClassLoader()
                                              .getResourceAsStream(url))
                              .map(this::getManifest)
                              .single()
                              .orElse(null);
        } catch (Exception e) {
            logger.warn("Warning : can't load manifest due to exception {}", e.getMessage());
        }
        return null;

    }

    public Map<String, String> getManifest(final InputStream input) {

        final Map<String, String> retMap = new HashMap<String, String>();
        try {
            Manifest manifest = new Manifest();
            manifest.read(input);
            final Attributes attributes = manifest.getMainAttributes();
            for (final Map.Entry attribute : attributes.entrySet()) {
                retMap.put(attribute.getKey()
                                    .toString(),
                           attribute.getValue()
                                    .toString());
            }
        } catch (final Exception ex) {
            logger.error("Failed to load manifest ", ex);
        }

        return retMap;
    }
}
