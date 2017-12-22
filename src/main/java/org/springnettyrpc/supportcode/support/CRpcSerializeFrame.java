package org.springnettyrpc.supportcode.support;

import io.netty.channel.ChannelPipeline;

/**
 * RPC消息序序列化协议选择器接口
 * @author ZHS
 * @create 2017-12-15 09:58
 */
public interface CRpcSerializeFrame {
    public void select(CRpcSerializeProtocol protocol, ChannelPipeline pipeline);
}
