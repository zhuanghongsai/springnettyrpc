package org.springnettyrpc.supportcode.core;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springnettyrpc.supportcode.hessian.CHessianCodecUtil;
import org.springnettyrpc.supportcode.hessian.CHessianDecoder;
import org.springnettyrpc.supportcode.hessian.CHessianEncoder;
import org.springnettyrpc.supportcode.kryo.CKryoCodecUtil;
import org.springnettyrpc.supportcode.kryo.CKryoDecoder;
import org.springnettyrpc.supportcode.kryo.CKryoEncoder;
import org.springnettyrpc.supportcode.kryo.CKryoPoolFactory;
import org.springnettyrpc.supportcode.support.CMessageCodecUtil;
import org.springnettyrpc.supportcode.support.CRpcSerializeFrame;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;

import java.util.Map;

/**
 * RPC服务端消息序列化协议框架
 * @author ZHS
 * @create 2017-12-15 11:04
 */
public class CRpcRecvSerializeFrame implements CRpcSerializeFrame {
    private Map<String, Object> handlerMap = null;

    public CRpcRecvSerializeFrame(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    //后续可以优化成通过spring ioc方式注入
    public void select(CRpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol) {
            case JDKSERIALIZE: {
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, CMessageCodecUtil.MESSAGE_LENGTH, 0, CMessageCodecUtil.MESSAGE_LENGTH));
                pipeline.addLast(new LengthFieldPrepender(CMessageCodecUtil.MESSAGE_LENGTH));
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                pipeline.addLast(new CMessageRecvHandler(handlerMap));
                break;
            }
            case KRYOSERIALIZE: {
                CKryoCodecUtil util = new CKryoCodecUtil(CKryoPoolFactory.getKryoPoolInstance());
                pipeline.addLast(new CKryoEncoder(util));
                pipeline.addLast(new CKryoDecoder(util));
                pipeline.addLast(new CMessageRecvHandler(handlerMap));
                break;
            }
            case HESSIANSERIALIZE: {
                CHessianCodecUtil util = new CHessianCodecUtil();
                pipeline.addLast(new CHessianEncoder(util));
                pipeline.addLast(new CHessianDecoder(util));
                pipeline.addLast(new CMessageRecvHandler(handlerMap));
                break;
            }
        }
    }
}
