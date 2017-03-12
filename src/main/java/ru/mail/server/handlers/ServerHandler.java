package ru.mail.server.handlers;

import java.io.File;
import java.io.FileInputStream;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.handler.stream.ChunkedStream;
import ru.mail.server.util.HttpHeaders;
import ru.mail.server.util.HttpRequest;
import ru.mail.server.util.HttpResponse;

public class ServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {

		HttpResponse httpResponse = new HttpResponse.HttpResponseBuilder().httpRequest(request).build();

		String h = httpResponse.getHttpHeaders().getHttpHeaders().toString();
		System.out.println(h);


		ctx.write(Unpooled.copiedBuffer(h.getBytes()));

		final FileInputStream is = httpResponse.getFileInputStream();
		if (is != null && !httpResponse.getMethodName().equals("HEAD")) {
			ChannelFuture future;
			future = ctx.writeAndFlush(new DefaultFileRegion(is.getChannel(), 0, httpResponse.getFileLenght()));

			future.addListener(ChannelFutureListener.CLOSE);
		} else {
			ctx.flush();
			ctx.close();
		}
	}
}