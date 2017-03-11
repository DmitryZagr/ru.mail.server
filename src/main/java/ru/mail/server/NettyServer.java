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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NettyServer {

	private static Logger log = Logger.getLogger(NettyServer.class.getName());
	private static String TAG = NettyServer.class.getName() + ": ";
	private static int port = 19000;

  public static void main(String[] args) throws IOException, InterruptedException {
    NioEventLoopGroup boosGroup = new NioEventLoopGroup();
    NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    ServerBootstrap bootstrap = new ServerBootstrap();
    bootstrap.group(boosGroup, workerGroup);
    bootstrap.channel(NioServerSocketChannel.class);

    // ===========================================================
    // 1. define a separate thread pool to execute handlers with
    //    slow business logic. e.g database operation
    // ===========================================================
    final EventExecutorGroup group = new DefaultEventExecutorGroup(1500); //thread pool of 1500

    bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
      @Override
      protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("stringDecoder", new StringDecoder());

        //===========================================================
        // 2. run handler with slow business logic
        //    in separate thread from I/O thread
        //===========================================================
        pipeline.addLast(group,"serverHandler",new ServerHandler());
      }
    });

    bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
    log.log(Level.INFO, TAG + "Netty Server bind to port " + port);
    bootstrap.bind(port).sync();

  }
}