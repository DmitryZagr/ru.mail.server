package ru.mail.server.handlers;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import ru.mail.server.util.HttpRequest;

public class HttpParaserHandler extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		HttpRequest httpReq = new HttpRequest.HttpRequestBuilder().build(msg);
		System.out.println(msg);
		out.add(httpReq);
	}

}
