package com.oath.micro.server.application.registry;

import java.io.File;
import java.util.Date;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.oath.micro.server.rest.jackson.JacksonUtil;

@Component
public class Cleaner {

    private final RegisterConfig config;
    private final int maxLive;

    @Autowired
    public Cleaner(RegisterConfig config,
        @Value("${service.registry.entry.max.live:43200000}") int maxLive) {
        this.config = config;
        this.maxLive = maxLive;
    }

    @PostConstruct
    public void deleteOldFilesAfterEachStartup() {
        cleanDir(new File(config.getOutputDir()), true);
    }

    public void clean() {
        cleanDir(new File(config.getOutputDir()), false);
    }

    private void cleanDir(File dir, boolean deleteWithoutCheck) {
        if (dir.listFiles() != null) {
            Stream.of(dir.listFiles()).forEach((next) -> {
                if (next.isDirectory()) {
                    cleanDir(next, deleteWithoutCheck);
                }

                if (next.isFile()) {
                    if (deleteWithoutCheck) {
                        next.delete();
                    } else {
                        checkFile(next);
                    }
                }

            });
        }
    }

    private void checkFile(File f) {
        try {
            RegisterEntry entry = JacksonUtil
                .convertFromJson(FileUtils.readFileToString(f), RegisterEntry.class);
            if (new Date().getTime() - maxLive > entry.getTime().getTime()) {
                f.delete();
            }
        } catch (Exception e) {
            f.delete();
        }
    }
}
