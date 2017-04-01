package ru.mail.server.handlers;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import ru.mail.server.util.HttpRequest;

public class HttpParserHandler extends MessageToMessageDecoder<String> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		HttpRequest httpReq = new HttpRequest.HttpRequestBuilder().build(msg);
		out.add(httpReq);
	}

}
