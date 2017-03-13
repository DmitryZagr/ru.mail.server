package ru.mail.application.util;

public class ParseCmd {

	private int port = 80;
	private String rootDir = System.getProperty("java.io.tmpdir");
	private int countOfThreads = Runtime.getRuntime().availableProcessors();

	public void parse(String[] args) {

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-r")) {
				rootDir = args[i + 1];
			} else if (args[i].equals("-c")) {
				countOfThreads = Integer.parseInt(args[i + 1]);
			} else if (args[i].equals("-p")) {
				port = Integer.parseInt(args[i + 1]);
			}
		}
	}

	public final int getPort() {
		return port;
	}

	public final String getRootDir() {
		return rootDir;
	}

	public final int getCountOfThreads() {
		return countOfThreads;
	}

}
