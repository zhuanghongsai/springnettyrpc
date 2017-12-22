package org.springnettyrpc.supportcode.core;

import com.google.common.reflect.Reflection;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;

/**
 * Rpc客户端执行模块
 * @author ZHS
 * @create 2017-12-15 11:08
 */
public class CMessageSendExecutor {
    private CRpcServerLoader loader = CRpcServerLoader.getInstance();

    public CMessageSendExecutor() {
    }

    public CMessageSendExecutor(String serverAddress, CRpcSerializeProtocol serializeProtocol) {
        loader.load(serverAddress, serializeProtocol);
    }

    public void setRpcServerLoader(String serverAddress, CRpcSerializeProtocol serializeProtocol) {
        loader.load(serverAddress, serializeProtocol);
    }

    public void stop() {
        loader.unLoad();
    }

    public static <T> T execute(Class<T> rpcInterface) {
        return (T) Reflection.newProxy(rpcInterface, new CMessageSendProxy<T>());
    }
}
