package com.oath.micro.server.jackson;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;


public interface JacksonMapperConfigurator extends Consumer<ObjectMapper> {


}
