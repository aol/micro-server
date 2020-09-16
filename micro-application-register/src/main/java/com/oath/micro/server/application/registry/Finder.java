package com.oath.micro.server.application.registry;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cyclops.reactive.collections.mutable.ListX;
import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oath.micro.server.rest.jackson.JacksonUtil;

@Component
public class Finder {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RegisterConfig config;

    @Autowired
    public Finder(RegisterConfig config) {
        this.config = config;
    }

    public List<RegisterEntry> find(final Optional<RegisterEntry> re) {
        try {
            List<RegisterEntry> entries = findDir(new File(config.getOutputDir()));
            if (re.isPresent()) {
                entries = entries.stream().filter(e -> e.matches(re.get()))
                                 .collect(Collectors.toList());
            }
            return entries;
        } catch (Exception e) {
            logger.error("Failed to find entries. Error: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private List<RegisterEntry> findDir(File dir) {
        List<RegisterEntry> result = new ArrayList<>();

        Stream.of(dir.listFiles()).forEach(
            (next) -> {

                if (next.isDirectory()) {
                    result.addAll(findDir(next));
                }
                if (next.isFile()) {
                    try {
                        String fileString = FileUtils.readFileToString(next, Charset.defaultCharset());
                        result.add(JacksonUtil.convertFromJson(fileString, RegisterEntry.class));
                    } catch (Exception e) {
                        logger.error("Error loading service entry from disk {}, Error: {}", next.getAbsolutePath(), e.getMessage(), e);
                    }
                }
            });
        return ListX.fromIterable(result);
    }
}
