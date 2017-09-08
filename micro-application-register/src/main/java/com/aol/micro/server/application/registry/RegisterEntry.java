package com.aol.micro.server.application.registry;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.QueryParam;
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

    private static SimpleDateFormat f = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    @Wither
    private final int port;
    @Wither
    private final String hostname;
    @Wither
    private final String module;
    @Wither
    private final String context;
    private final Date time;
    @Wither
    private final String uuid;
    @Wither
    private final String target;
    private final String formattedDate;
    private final Map<String, String> manifest = ManifestLoader.instance.getManifest();
    @Wither
    private final Health health;
    @Wither
    private final List<Map<String, Map<String, String>>> stats;
    @Wither
    private final int externalPort;

    public RegisterEntry() {
        this(-1, null, null, null, null, null, null, -1);
    }

    public RegisterEntry(int port, String hostname, String module, String context, Date time, String uuid,
                         String target, int externalPort) {
        this(port, hostname, module, context, time, UUID.randomUUID().toString(), target, null, Health.OK, null, externalPort);
    }

    public RegisterEntry(int port, String hostname, String module, String context, Date time, String target,
                         int externalPort) {
        this(port, hostname, module, context, time, UUID.randomUUID().toString(), target, externalPort);
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

    public boolean matches(RegisterEntry re) {
        return  (re.port == -1 || re.port == port) &&
                (Objects.isNull(re.hostname) || Objects.nonNull(hostname) && hostname.startsWith(re.hostname)) &&
                (Objects.isNull(re.module) || Objects.nonNull(module) && module.startsWith(re.module)) &&
                (Objects.isNull(re.context) || Objects.nonNull(context) && context.startsWith(re.context)) &&
                (Objects.isNull(re.health) || re.health.equals(health)) &&
                (re.externalPort == -1 || re.externalPort != externalPort) &&
                (Objects.isNull(re.manifest) || (
                        (!re.manifest.containsKey("Implementation-revision")) || re.manifest.get("Implementation-revision").equals(manifest.get("Implementation-revision")) &&
                        (!re.manifest.containsKey("Implementation-Timestamp")) || re.manifest.get("Implementation-Timestamp").equals(manifest.get("Implementation-Timestamp")) &&
                        (!re.manifest.containsKey("Implementation-Version")) || re.manifest.get("Implementation-Version").equals(manifest.get("Implementation-Version"))
                ));
    }

}
