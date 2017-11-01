package app.loader.scheduled.off.com.aol.micro.server;

import org.springframework.stereotype.Component;

import com.oath.micro.server.async.data.loader.ConditionallyLoad;

@Component
public class TurnOff implements ConditionallyLoad {

    @Override
    public boolean shouldLoad() {
        return false;
    }

}
