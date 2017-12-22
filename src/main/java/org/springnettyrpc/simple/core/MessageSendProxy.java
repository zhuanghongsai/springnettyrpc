package org.springnettyrpc.simple.core;

import org.springnettyrpc.simple.model.MessageRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Rpc客户端消息处理
 * @author ZHS
 * @create 2017-12-11 16:39
 */
public class MessageSendProxy<T> implements InvocationHandler {
    private Class<T> cls;

    public MessageSendProxy(Class<T> cls) {
        this.cls = cls;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParameters(args);

        MessageSendHandler handler = RpcServerLoader.getInstance().getMessageSendHandler();
        MessageCallBack callBack = handler.sendRequest(request);
        return callBack.start();
    }
}
