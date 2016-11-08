package app.com.aol.micro.server.log.alias;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aol.micro.server.log.LogLookup;

@Component
public class CustomAlias implements LogLookup {

    private final Map<String, File> configuredAliases = new HashMap<String, File>() {
        {
            put("custom1", new File(
                                    "/tmp/tailer-test-file2"));
        }
    };

    @Override
    public File lookup(String alias) {
        return configuredAliases.get(alias);
    }

}
