package com.oath.micro.server.application.registry;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Component
public class RegisterConfig {

    String outputDir;

    @Autowired
    public RegisterConfig(
        @Value("${service.registry.dir:#{systemProperties['java.io.tmpdir']}/services}") String outputDir) {
        this.outputDir = outputDir;
    }

}
