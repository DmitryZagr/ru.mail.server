package ru.mail.server.handlers;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mail.server.util.HttpRequest;

public class ServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(msg);
//		msg = "HTTP/1.1 200 OK\n\r" + "Connection: Closed";
//		ctx.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
	}
}