package ru.mail.application;

import ru.mail.application.util.ParseCmd;
import ru.mail.server.NettyServer;

public class App {

	public static void main(String[] args) {

		ParseCmd parser = new ParseCmd();
		parser.parse(args);

		NettyServer server = new NettyServer.NettyServerBuilder().port(parser.getPort())
				.threads(parser.getCountOfThreads()).rootDir(parser.getRootDir()).build();
		server.start();
	}

}
