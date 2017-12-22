package org.springnettyrpc.supportcode.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * RPC消息序列化/反序列化接口定义
 * @author ZHS
 * @create 2017-12-15 09:56
 */
public interface CRpcSerialize {

    void serialize(OutputStream output, Object object) throws IOException;

    Object deserialize(InputStream input) throws IOException;
}
