package ru.mail.server.handlers;

import java.io.FileInputStream;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mail.server.cache.ServerMemoryCache;
import ru.mail.server.util.HttpRequest;
import ru.mail.server.util.HttpResponse;
import ru.mail.server.util.WriteHttpResponse;

public class ServerHandler extends SimpleChannelInboundHandler<HttpRequest> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {

		ServerMemoryCache cache = ServerMemoryCache.getInstance();

//		if(cache.get(request.getOriginalHttpRequest()) == null) {
//
//		}

		HttpResponse httpResponse = new HttpResponse.HttpResponseBuilder().httpRequest(request).build();


		WriteHttpResponse.write(ctx, httpResponse);
	}
}