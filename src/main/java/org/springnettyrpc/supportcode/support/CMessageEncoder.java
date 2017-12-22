package org.springnettyrpc.supportcode.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC消息编码接口
 * @author ZHS
 * @create 2017-12-15 09:59
 */
public class CMessageEncoder extends MessageToByteEncoder<Object> {


    private CMessageCodecUtil util = null;

    public CMessageEncoder(final CMessageCodecUtil util) {
        this.util = util;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        util.encode(out, msg);
    }
}
