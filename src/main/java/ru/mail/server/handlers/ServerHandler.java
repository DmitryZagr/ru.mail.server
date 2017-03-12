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
		// TODO Auto-generated method stub

		HttpHeaders headers = new HttpHeaders.HttpHeadersBuilder().connection("Closed")
				.contentLenght(Integer.toString(0)).build(404);
		// msg = "HTTP/1.1 200 OK\n\r" + "Connection: Closed";
		String h = headers.getHttpHeaders().toString();
		System.out.println(h);
		ctx.write(Unpooled.copiedBuffer(h.getBytes()));
		ctx.writeAndFlush(Unpooled.copiedBuffer(h.getBytes()));
		ctx.close();
	}
}