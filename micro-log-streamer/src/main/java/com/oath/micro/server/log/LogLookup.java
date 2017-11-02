package com.oath.micro.server.log;

import java.io.File;

public interface LogLookup {

    public File lookup(String alias);
}
