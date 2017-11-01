package com.oath.micro.server.application.registry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.oath.cyclops.util.ExceptionSoftener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import com.oath.micro.server.servers.ApplicationRegister;
import com.oath.micro.server.servers.model.ServerData;

import lombok.Getter;

@Component
public class ApplicationRegisterImpl implements ApplicationRegister {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Getter
    private volatile Application application;

    private final String customHostname;
    private final String targetEndpoint;

    private final Properties props;

    @Autowired
    public ApplicationRegisterImpl(@Value("${host.address:#{null}}") String customHostname,
            @Value("${target.endpoint:#{null}}") String targetEndpoint,
            @Qualifier("propertyFactory") Properties props) {

        this.customHostname = customHostname;
        this.targetEndpoint = targetEndpoint;
        this.props = props;
    }

    public ApplicationRegisterImpl() {
        this(
             null, null, new Properties());
    }

    @Override
    public void register(ServerData[] data) {

        try {
            final String hostname = Optional.ofNullable(customHostname)
                                            .orElse(InetAddress.getLocalHost()
                                                               .getHostName());

            application = new Application(
                                          Stream.of(data)
                                                .map(next -> new RegisterEntry(
                                                                               next.getPort(), hostname,
                                                                               next.getModule()
                                                                                   .getContext(),
                                                                               next.getModule()
                                                                                   .getContext(),
                                                                               null, targetEndpoint,
                                                                               externalPort(next)))
                                                .collect(Collectors.toList()));
            logger.info("Registered application {} ", application);
        } catch (UnknownHostException e) {
            throw ExceptionSoftener.throwSoftenedException(e);
        }
    }

    private int externalPort(ServerData next) {
        String ep = props.getProperty("external.port." + next.getModule()
                                                             .getContext());
        if (ep == null)
            return next.getPort();
        try {
            return Integer.valueOf(ep);
        } catch (NumberFormatException e) {
            return next.getPort();
        }

    }
}
