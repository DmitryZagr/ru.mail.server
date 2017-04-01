package ru.mail.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import ru.mail.server.handlers.HttpParserHandler;
import ru.mail.server.handlers.ServerHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NettyServer {

	private static Map<String, ServerConfig> serversConfigs = new HashMap<>();

	private static Logger log = Logger.getLogger(NettyServer.class.getName());
	private static String TAG = NettyServer.class.getName() + ": ";

	private int port = 80;
	private String rootDir = System.getProperty("java.io.tmpdir");
	private int countOfThreads = Runtime.getRuntime().availableProcessors();
	private String serverName;

	public static class NettyServerBuilder {

		private int port = 80;
		private String rootDir = System.getProperty("java.io.tmpdir");
		private int countOfThreads = Runtime.getRuntime().availableProcessors();
		private String serverName = "NettyServer";

		public NettyServerBuilder port(int port) {
			this.port = port;
			return this;
		}

		public NettyServerBuilder rootDir(String rootDir) {
			this.rootDir = rootDir;
			return this;
		}

		public NettyServerBuilder threads(int threads) {
			this.countOfThreads = threads;
			return this;
		}

		public NettyServerBuilder serverName(String serverName) {
			this.serverName = serverName;
			return this;
		}

		public NettyServer build() {
			return new NettyServer(port, rootDir, countOfThreads, serverName);
		}
	}

	public static ServerConfig getServerConfigByName(String name) {
		return serversConfigs.get(name);
	}

	private NettyServer(int port, String rootDir, int countOfThreads, String serverName) {
		this.port = port;
		this.rootDir = rootDir;
		this.countOfThreads = countOfThreads;
		this.serverName = serverName;
		serversConfigs.put(serverName, new ServerConfig(port, rootDir));
	}

	public void start() {
		NioEventLoopGroup boosGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup(this.countOfThreads);
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boosGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);

		// ===========================================================
		// 1. define a separate thread pool to execute handlers with
		// slow business logic. e.g database operation
		// ===========================================================
		final EventExecutorGroup groupServerHandler = new DefaultEventExecutorGroup(this.countOfThreads);
		final EventExecutorGroup groupStringDecoder = new DefaultEventExecutorGroup(this.countOfThreads);
		final EventExecutorGroup groupHttpParser = new DefaultEventExecutorGroup(this.countOfThreads);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(groupStringDecoder, "stringDecoder", new StringDecoder());
				pipeline.addLast(groupHttpParser, "httpParaser", new HttpParserHandler());

				// ===========================================================
				// 2. run handler with slow business logic
				// in separate thread from I/O thread
				// ===========================================================
				pipeline.addLast(groupServerHandler, "serverHandler", new ServerHandler());
			}
		});

		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true);

		StringBuilder msg = new StringBuilder();
		msg.append(TAG).append("Netty Server bind to port: ").append(port).append(",\n").append("root dir: ")
				.append(rootDir).append(",\n").append("workers: ").append(this.countOfThreads).append("\n");

		log.log(Level.INFO, msg.toString());
		try {
			bootstrap.bind(port).sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final class ServerConfig {

		private int port;
		private String rootDir;

		private ServerConfig(int port, String rootDir) {
			this.port = port;
			this.rootDir = rootDir;
		}

		public int getPort() {
			return port;
		}

		public String getRootDir() {
			return rootDir;
		}

	}

}