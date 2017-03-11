package ru.mail.application;

import ru.mail.server.NettyServer;

public class App {

	public static void main(String[] args) {
		NettyServer server = new NettyServer.NettyServerBuilder().port(8080).threads(4).build();
		server.start();
	}

}
