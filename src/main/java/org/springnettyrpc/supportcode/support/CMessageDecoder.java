package org.springnettyrpc.supportcode.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ZHS
 * @create 2017-12-15 10:00
 */
public class CMessageDecoder extends ByteToMessageDecoder {

    final public static int MESSAGE_LENGTH = CMessageCodecUtil.MESSAGE_LENGTH;
    private CMessageCodecUtil util = null;

    public CMessageDecoder(final CMessageCodecUtil util) {
        this.util = util;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //出现粘包导致消息头长度不对，直接返回
        if (in.readableBytes() < CMessageDecoder.MESSAGE_LENGTH) {
            return;
        }

        in.markReaderIndex();
        //读取消息的内容长度
        int messageLength = in.readInt();

        if (messageLength < 0) {
            ctx.close();
        }

        //读到的消息长度和报文头的已知长度不匹配。那就重置一下ByteBuf读索引的位置
        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);

            try {
                Object obj = util.decode(messageBody);
                out.add(obj);
            } catch (IOException ex) {
                Logger.getLogger(CMessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
