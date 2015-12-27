package com.aol.micro.server.alivehandler;

import java.util.Optional;

public interface AliveHandlerControllerRepository {
	Optional<AliveHandlerController> get(String name);
}
