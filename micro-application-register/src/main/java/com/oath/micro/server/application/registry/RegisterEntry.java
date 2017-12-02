package com.oath.micro.server.application.registry;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "register-entry")
@XmlType(name = "")
@Getter
@Wither
@Builder
@ToString
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
    private final Map<String, String> manifest = new HashMap<>();
    @Wither
    private final Health health;
    @Wither
    private final List<Map<String, Map<String, String>>> stats;
    @Wither
    private final int externalPort;

    public RegisterEntry() {
        this(-1, null, null, null, null, null, null, -1);
    }

    public RegisterEntry(int port, String hostname, String module, String context, Date time,
        String uuid,
        String target, int externalPort) {
        this(port, hostname, module, context, time, uuid, target, null, Health.OK, null,
            externalPort);
    }

    public RegisterEntry(int port, String hostname, String module, String context, Date time,
        String target,
        int externalPort) {
        this(port, hostname, module, context, time, UUID.randomUUID().toString(), target,
            externalPort);
    }

    private RegisterEntry(int port, String hostname, String module, String context, Date time,
        String uuid,
        String target, String ignoreDate, Health health,
        List<Map<String, Map<String, String>>> stats,
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

        if (time != null) {
            this.formattedDate = f.format(this.time);
        } else {
            this.formattedDate = null;
        }

        this.manifest.putAll(ManifestLoader.instance.getManifest());

    }

    public boolean matches(RegisterEntry re) {
        //Only the fields which make sense to query is added for now.
        return (re.port == -1 || re.port == port) &&
            (isNull(re.hostname) || nonNull(hostname) && hostname.startsWith(re.hostname)) &&
            (isNull(re.module) || nonNull(module) && module.startsWith(re.module)) &&
            (isNull(re.context) || nonNull(context) && context.startsWith(re.context)) &&
            (isNull(re.health) || re.health.equals(health)) &&
            (re.externalPort == -1 || re.externalPort == externalPort) &&
            (isNull(re.manifest) || re.manifest.isEmpty() || matchManifest(re.manifest));
    }

    private boolean matchManifest(Map<String, String> manifest) {
        return match(manifest, this.manifest, "Implementation-revision") &&
            match(manifest, this.manifest, "Implementation-Timestamp") &&
            match(manifest, this.manifest, "Implementation-Version");
    }

    private boolean match(Map<String, String> map1, Map<String, String> map2, String key) {
        return !map1.containsKey(key) || (map2.containsKey(key) && map2.get(key)
            .startsWith(map1.get(key)));
    }
}
