package app.com.aol.micro.server.dist.lock.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oath.micro.server.dist.lock.DistributedLockManager;
import com.oath.micro.server.dist.lock.DistributedLockService;

@Configuration
public class ConfigureDistLock {

    @Autowired
    private DistributedLockService distributedLockService;

    @Bean
    public DistributedLockManager manager() {
        return new DistributedLockManager(
                                          distributedLockService, "dummy-key");
    }
}
