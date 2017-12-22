package org.springnettyrpc.supportcode.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * Rpc客户端线程任务处理
 * @author ZHS
 * @create 2017-12-15 11:09
 */
public class MessageSendInitializeTask implements Callable<Boolean> {

    private EventLoopGroup eventLoopGroup = null;
    private InetSocketAddress serverAddress = null;
    private CRpcSerializeProtocol protocol;

    MessageSendInitializeTask(EventLoopGroup eventLoopGroup, InetSocketAddress serverAddress, CRpcSerializeProtocol protocol) {
        this.eventLoopGroup = eventLoopGroup;
        this.serverAddress = serverAddress;
        this.protocol = protocol;
    }

    public Boolean call() {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new CMessageSendChannelInitializer().buildRpcSerializeProtocol(protocol));

        ChannelFuture channelFuture = b.connect(serverAddress);
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    CMessageSendHandler handler = channelFuture.channel().pipeline().get(CMessageSendHandler.class);
                    CRpcServerLoader.getInstance().setMessageSendHandler(handler);
                }
            }
        });
        return Boolean.TRUE;
    }
}
