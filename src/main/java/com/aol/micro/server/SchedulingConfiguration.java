package com.aol.micro.server;

import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;

public interface SchedulingConfiguration extends SchedulingConfigurer, AsyncConfigurer{

}
