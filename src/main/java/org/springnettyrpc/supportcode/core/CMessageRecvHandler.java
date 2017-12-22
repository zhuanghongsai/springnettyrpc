package org.springnettyrpc.supportcode.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springnettyrpc.supportcode.model.CMessageRequest;
import org.springnettyrpc.supportcode.model.CMessageResponse;

import java.util.Map;

/**
 * Rpc服务器消息处理
 * @author ZHS
 * @create 2017-12-15 11:01
 */
public class CMessageRecvHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, Object> handlerMap;

    public CMessageRecvHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CMessageRequest request = (CMessageRequest) msg;
        CMessageResponse response = new CMessageResponse();
        CMessageRecvInitializeTask recvTask = new CMessageRecvInitializeTask(request, response, handlerMap);
        //不要阻塞nio线程，复杂的业务逻辑丢给专门的线程池
        CMessageRecvExecutor.submit(recvTask, ctx, request, response);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //网络有异常要关闭通道
        ctx.close();
    }
}
