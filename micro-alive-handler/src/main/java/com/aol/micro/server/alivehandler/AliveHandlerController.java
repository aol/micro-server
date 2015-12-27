package com.aol.micro.server.alivehandler;

import javax.ws.rs.core.Response;

public interface AliveHandlerController {
	Response process();
	void enable();
	void disable();
}
