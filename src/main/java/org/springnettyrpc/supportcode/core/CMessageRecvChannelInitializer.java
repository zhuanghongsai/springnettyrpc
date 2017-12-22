package org.springnettyrpc.supportcode.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;

import java.util.Map;

/**
 * Rpc服务端管道初始化
 * @author ZHS
 * @create 2017-12-15 10:53
 */
public class CMessageRecvChannelInitializer extends ChannelInitializer<SocketChannel> {

    private CRpcSerializeProtocol protocol;
    private CRpcRecvSerializeFrame frame = null;


    CMessageRecvChannelInitializer buildRpcSerializeProtocol(CRpcSerializeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }

    CMessageRecvChannelInitializer(Map<String, Object> handlerMap) {
        frame = new CRpcRecvSerializeFrame(handlerMap);
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        frame.select(protocol, pipeline);
    }
}
