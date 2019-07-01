package com.oath.micro.server.application.registry;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.oath.micro.server.rest.client.nio.AsyncRestClient;
import com.oath.micro.server.rest.jackson.JacksonUtil;

@Component
public class Job {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AsyncRestClient rest = new AsyncRestClient(
        100, 2000);
    private final String apiUrl;
    private final ApplicationRegisterImpl app;
    private final String uuid = UUID.randomUUID()
        .toString();
    private final String resourcePath;
    private final RegistryHealthChecker checker;
    private final RegistryStatsChecker statsChecker;
    @Getter
    private int scheduled =0;

    @Autowired
    public Job(@Value("${service.registry.url:#{null}}") String apiUrl, ApplicationRegisterImpl app,
        @Value("${resource.path:/service-registry/register}") String resourcePath,
        RegistryHealthChecker checker,
        RegistryStatsChecker statsChecker) {

        this.apiUrl = apiUrl;
        this.app = app;
        this.resourcePath = resourcePath;
        this.checker = checker;
        this.statsChecker = statsChecker;

    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${service.registry.delay:1000}")
    public synchronized void schedule() {
        scheduled++;
        try {
            if (app.getApplication() != null && apiUrl != null) {
                app.getApplication()
                    .forEach(moduleEntry -> sendPing(moduleEntry));
                sent++;
            }
        } catch (Exception e) {
            errors++;
            logger.error("Failed to register services due to exception {}", e.getMessage(), e);
        }
    }

    private void sendPing(RegisterEntry moduleEntry) {
        final RegisterEntry entry = moduleEntry.withTime(new Date())
            .withUuid(uuid)
            .withHealth(checker.isOk() ? Health.OK : Health.ERROR)
            .withStats(nonEmptyOrNull(statsChecker.stats()));
        try {

            logger.debug("Posting {} to " + apiUrl + resourcePath,
                JacksonUtil.serializeToJson(entry));
            rest.post(apiUrl + resourcePath, JacksonUtil.serializeToJson(entry))
                .join();
        } catch (Exception e) {
            logger
                .warn("Failed posting {} to {}" + resourcePath, JacksonUtil.serializeToJson(entry),
                    apiUrl);

        }
    }

    private List<Map<String, Map<String, String>>> nonEmptyOrNull(
        List<Map<String, Map<String, String>>> stats) {
        if (stats == null || stats.isEmpty()) {
            return null;
        }
        return stats;
    }
}
