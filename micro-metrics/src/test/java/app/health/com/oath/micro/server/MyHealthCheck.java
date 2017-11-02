package app.health.com.oath.micro.server;

import org.springframework.stereotype.Component;

import com.codahale.metrics.health.HealthCheck;

@Component
public class MyHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
