package org.springnettyrpc.simple.core;

import java.lang.reflect.Proxy;

/**
 * Rpc客户端执行模块
 * @author ZHS
 * @create 2017-12-11 16:37
 */
public class MessageSendExecutor {
    private RpcServerLoader loader = RpcServerLoader.getInstance();

    public MessageSendExecutor(String serverAddress) {
        loader.load(serverAddress);
    }

    public void stop() {
        loader.unLoad();
    }

    public static <T> T execute(Class<T> rpcInterface) {
        return (T) Proxy.newProxyInstance(
                rpcInterface.getClassLoader(),
                new Class<?>[]{rpcInterface},
                new MessageSendProxy<T>(rpcInterface)
        );
    }
}
