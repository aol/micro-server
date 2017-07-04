package com.aol.micro.server.elasticache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class ConfigureCacheTester {

    @Autowired
    private ElasticacheConnectionTester elasticacheConnectionTester;

    @Value("${elasticache.connection.checker.frequency:60000}")
    private final int fixedDelay = 60000;

    @Scheduled(fixedDelay = fixedDelay)
    public synchronized void runElasticacheConnectionTester(){
        elasticacheConnectionTester.scheduleAndLog();
    }
}
