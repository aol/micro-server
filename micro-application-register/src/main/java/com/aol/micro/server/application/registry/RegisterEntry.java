package com.aol.micro.server.application.registry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "register-entry")
@XmlType(name = "")
@Getter
@Wither
@Builder
public class RegisterEntry {

    private static SimpleDateFormat f = new SimpleDateFormat(
                                                             "EEE, d MMM yyyy HH:mm:ss Z");
    @Wither
    int port;
    @Wither
    String hostname;
    @Wither
    String module;
    @Wither
    String context;
    Date time;
    @Wither
    String uuid;
    @Wither
    String target;
    String formattedDate;
    Map<String, String> manifest = ManifestLoader.instance.getManifest();
    @Wither
    Health health;
    @Wither
    List<Map<String, Map<String, String>>> stats;
    @Wither
    int externalPort;

    public RegisterEntry() {
        this(
             -1, null, null, null, null, null, null, -1);
    }

    public RegisterEntry(int port, String hostname, String module, String context, Date time, String uuid,
            String target, int externalPort) {
        this(
             port, hostname, module, context, time, UUID.randomUUID()
                                                        .toString(),
             target, null, Health.OK, null, externalPort);
    }

    private RegisterEntry(int port, String hostname, String module, String context, Date time, String uuid,
            String target, String ignoreDate, Health health, List<Map<String, Map<String, String>>> stats,
            int externalPort) {
        this.port = port;
        this.hostname = hostname;
        this.module = module;
        this.context = context;
        this.time = time;
        this.uuid = uuid;
        this.target = target;
        this.health = health;
        this.stats = stats;
        this.externalPort = externalPort;

        if (time != null)
            this.formattedDate = f.format(this.time);
        else
            this.formattedDate = null;

    }

    public RegisterEntry(int port, String hostname, String module, String context, Date time, String target,
            int externalPort) {
        this(
             port, hostname, module, context, time, UUID.randomUUID()
                                                        .toString(),
             target, externalPort);
    }

}
