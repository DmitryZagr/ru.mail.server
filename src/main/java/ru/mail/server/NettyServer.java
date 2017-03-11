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
import ru.mail.server.handlers.ServerHandler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NettyServer {

	private static Logger log = Logger.getLogger(NettyServer.class.getName());
	private static String TAG = NettyServer.class.getName() + ": ";

	private int port = 8080;
	private String rootDir = System.getProperty("java.io.tmpdir");
	private int countOfThreads = Runtime.getRuntime().availableProcessors();

	public static class NettyServerBuilder {

		private int port = 8080;
		private String rootDir = System.getProperty("java.io.tmpdir");
		private int countOfThreads = Runtime.getRuntime().availableProcessors();

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

		public NettyServer build() {
			return new NettyServer(port, rootDir, countOfThreads);
		}
	}

	private NettyServer(int port, String rootDir, int countOfThreads) {
		this.port = port;
		this.rootDir = rootDir;
		this.countOfThreads = countOfThreads;
	}

	public void start() {
		NioEventLoopGroup boosGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boosGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);

		// ===========================================================
		// 1. define a separate thread pool to execute handlers with
		// slow business logic. e.g database operation
		// ===========================================================
		final EventExecutorGroup group = new DefaultEventExecutorGroup(this.countOfThreads);

		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("stringDecoder", new StringDecoder());

				// ===========================================================
				// 2. run handler with slow business logic
				// in separate thread from I/O thread
				// ===========================================================
				pipeline.addLast(group, "serverHandler", new ServerHandler());
			}
		});

		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

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
}