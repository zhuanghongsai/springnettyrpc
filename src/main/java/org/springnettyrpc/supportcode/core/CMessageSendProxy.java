package org.springnettyrpc.supportcode.core;

import com.google.common.reflect.AbstractInvocationHandler;
import org.springnettyrpc.supportcode.model.CMessageRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Rpc客户端代理消息处理
 * @author ZHS
 * @create 2017-12-15 11:11
 */
public class CMessageSendProxy<T> extends AbstractInvocationHandler {

    public Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        CMessageRequest request = new CMessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParameters(args);

        CMessageSendHandler handler = CRpcServerLoader.getInstance().getMessageSendHandler();
        CMessageCallBack callBack = handler.sendRequest(request);
        return callBack.start();
    }
}
