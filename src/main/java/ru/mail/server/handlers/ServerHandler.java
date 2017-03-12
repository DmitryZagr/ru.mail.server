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

//		HttpHeaders headers = new HttpHeaders.HttpHeadersBuilder().code(404).connection("Closed").build();
//		String h = headers.getHttpHeaders().toString();
//		System.out.println(h);

		String h = httpResponse.getHttpHeaders().getHttpHeaders().toString();
		System.out.println(h);

		ctx.writeAndFlush(Unpooled.copiedBuffer(h.getBytes()));



		final FileInputStream is = httpResponse.getFileInputStream();
		 ChannelFuture future;
		 future = ctx.writeAndFlush(new DefaultFileRegion(is.getChannel(), 0, 22));

//		ChannelFuture future;



//		 final FileInputStream is = httpResponse.getFileInputStream();
//		if(is != null) {
//			 final Channel ch = ctx.channel();
//		        final ChannelFuture future;
////		        ch.write(response);
//		        future = ch.write(new ChunkedNioFile(new File("/Users/admin/Work/MailRu/3_semester/HighLoad/ROOT/lol.js")));
//		        future.addListener(new ChannelFutureListener() {
//		            @Override
//		            public void operationComplete(ChannelFuture future) throws Exception {
////		                future.getChannel().close();
//		                future.channel().close();
//		            }
//		        });
//
////		        future.addListener(ChannelFutureListener.CLOSE);
////		        if (!isKeepAlive(request)) {
////		            future.addListener(ChannelFutureListener.CLOSE);
////		        }
//		}

//		ctx.close();
	}
}