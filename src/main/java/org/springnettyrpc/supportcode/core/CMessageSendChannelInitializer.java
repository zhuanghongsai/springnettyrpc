package org.springnettyrpc.supportcode.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;

/**
 * Rpc客户端管道初始化
 * @author ZHS
 * @create 2017-12-15 11:07
 */
public class CMessageSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    private CRpcSerializeProtocol protocol;
    private CRpcSendSerializeFrame frame = new CRpcSendSerializeFrame();

    CMessageSendChannelInitializer buildRpcSerializeProtocol(CRpcSerializeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        frame.select(protocol, pipeline);
    }
}
