package org.springnettyrpc.supportcode.hessian;

import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;
import org.springnettyrpc.supportcode.support.CMessageCodecUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hessian编解码工具类
 * @author ZHS
 * @create 2017-12-15 10:50
 */
public class CHessianCodecUtil implements CMessageCodecUtil {
    CHessianSerializePool pool = CHessianSerializePool.getHessianPoolInstance();
    private static Closer closer = Closer.create();

    public CHessianCodecUtil() {

    }

    public void encode(final ByteBuf out, final Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            closer.register(byteArrayOutputStream);
            CHessianSerialize hessianSerialization = pool.borrow();
            hessianSerialization.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
            pool.restore(hessianSerialization);
        } finally {
            closer.close();
        }
    }

    public Object decode(byte[] body) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            closer.register(byteArrayInputStream);
            CHessianSerialize hessianSerialization = pool.borrow();
            Object object = hessianSerialization.deserialize(byteArrayInputStream);
            pool.restore(hessianSerialization);
            return object;
        } finally {
            closer.close();
        }
    }
}
