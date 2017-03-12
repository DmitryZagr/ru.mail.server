package ru.mail.server.handlers;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mail.server.util.HttpHeaders;
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
		HttpHeaders headers = new HttpHeaders.HttpHeadersBuilder().code(404).connection("Closed").build();
		String h = headers.getHttpHeaders().toString();
		System.out.println(h);
		ctx.writeAndFlush(Unpooled.copiedBuffer(h.getBytes()));
		ctx.close();
	}
}