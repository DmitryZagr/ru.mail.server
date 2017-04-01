package ru.mail.server.util;

import java.io.FileInputStream;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;

public class WriteHttpResponse {

	public static void write(ChannelHandlerContext ctx, HttpResponse httpResponse) {

		ctx.write(Unpooled.copiedBuffer(httpResponse.getHttpHeaders().getHttpHeaders().toString().getBytes()));

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
