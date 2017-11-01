package app.cleaner.off.scheduled.com.aol.micro.server;

import org.springframework.stereotype.Component;

import com.aol.micro.server.async.data.cleaner.ConditionallyClean;

@Component
public class TurnOff implements ConditionallyClean {

    @Override
    public boolean shouldClean() {

        return false;
    }

}
