package com.aol.micro.server.servers;

import com.aol.micro.server.servers.model.ServerData;

public interface ServerApplication {

	void run();
	ServerData getServerData();
}
