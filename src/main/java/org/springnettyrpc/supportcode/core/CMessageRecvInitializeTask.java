package org.springnettyrpc.supportcode.core;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springnettyrpc.supportcode.model.CMessageRequest;
import org.springnettyrpc.supportcode.model.CMessageResponse;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Rpc服务器消息线程任务处理
 * @author ZHS
 * @create 2017-12-15 11:02
 */
public class CMessageRecvInitializeTask implements Callable<Boolean> {

    private CMessageRequest request = null;
    private CMessageResponse response = null;
    private Map<String, Object> handlerMap = null;
    private ChannelHandlerContext ctx = null;

    public CMessageResponse getResponse() {
        return response;
    }

    public CMessageRequest getRequest() {
        return request;
    }

    public void setRequest(CMessageRequest request) {
        this.request = request;
    }

    CMessageRecvInitializeTask(CMessageRequest request, CMessageResponse response, Map<String, Object> handlerMap) {
        this.request = request;
        this.response = response;
        this.handlerMap = handlerMap;
        this.ctx = ctx;
    }

    public Boolean call() {
        response.setMessageId(request.getMessageId());
        try {
            Object result = reflect(request);
            response.setResultDesc(result);
            return Boolean.TRUE;
        } catch (Throwable t) {
            response.setError(t.toString());
            t.printStackTrace();
            System.err.printf("RPC Server invoke error!\n");
            return Boolean.FALSE;
        }
    }

    private Object reflect(CMessageRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        String methodName = request.getMethodName();
        Object[] parameters = request.getParameters();
        return MethodUtils.invokeMethod(serviceBean, methodName, parameters);
    }
}
