package com.aol.micro.server.log;

import java.io.File;
import java.util.Optional;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LogTailer {
    private final String fileLocation;
    private final Optional<LogLookup> logLookup;

    @Autowired(required = false)
    public LogTailer(@Value("${log.tailer.file.location:}") String fileLocation) {
        this.fileLocation = fileLocation;
        this.logLookup = Optional.empty();
    }

    @Autowired(required = false)
    public LogTailer(@Value("${log.tailer.file.location:}") String fileLocation, LogLookup logLookup) {
        this.fileLocation = fileLocation;
        this.logLookup = Optional.of(logLookup);
    }

    public void tail(TailerListener listener) {

        File file = new File(
                             fileLocation);
        Tailer tailer = Tailer.create(file, listener, 10);

        tailer.run();
    }

    public void tail(TailerListener listener, String alias) {
        File file = logLookup.map(l -> l.lookup(alias))
                             .orElse(new File(
                                              fileLocation));
        Tailer tailer = Tailer.create(file, listener, 10);
        tailer.run();
    }
}
