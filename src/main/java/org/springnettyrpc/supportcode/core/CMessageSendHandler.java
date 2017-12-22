package org.springnettyrpc.supportcode.core;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springnettyrpc.supportcode.model.CMessageRequest;
import org.springnettyrpc.supportcode.model.CMessageResponse;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZHS
 * @create 2017-12-15 11:16
 */
public class CMessageSendHandler  extends ChannelInboundHandlerAdapter {
    private ConcurrentHashMap<String, CMessageCallBack> mapCallBack = new ConcurrentHashMap<String, CMessageCallBack>();

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CMessageResponse response = (CMessageResponse) msg;
        String messageId = response.getMessageId();
        CMessageCallBack callBack = mapCallBack.get(messageId);
        if (callBack != null) {
            mapCallBack.remove(messageId);
            callBack.over(response);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public CMessageCallBack sendRequest(CMessageRequest request) {
        CMessageCallBack callBack = new CMessageCallBack(request);
        mapCallBack.put(request.getMessageId(), callBack);
        channel.writeAndFlush(request);
        return callBack;
    }
}
