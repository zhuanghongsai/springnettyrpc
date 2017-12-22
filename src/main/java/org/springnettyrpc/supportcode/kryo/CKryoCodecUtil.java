package org.springnettyrpc.supportcode.kryo;

import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.io.Closer;
import io.netty.buffer.ByteBuf;
import org.springnettyrpc.supportcode.support.CMessageCodecUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kryo编解码工具类
 * @author ZHS
 * @create 2017-12-15 10:33
 */
public class CKryoCodecUtil implements CMessageCodecUtil {
    private KryoPool pool;
    private static Closer closer = Closer.create();

    public CKryoCodecUtil(KryoPool pool) {
        this.pool = pool;
    }

    public void encode(final ByteBuf out, final Object message) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            closer.register(byteArrayOutputStream);
            CKryoSerialize kryoSerialization = new CKryoSerialize(pool);
            kryoSerialization.serialize(byteArrayOutputStream, message);
            byte[] body = byteArrayOutputStream.toByteArray();
            int dataLength = body.length;
            out.writeInt(dataLength);
            out.writeBytes(body);
        } finally {
            closer.close();
        }
    }

    public Object decode(byte[] body) throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            closer.register(byteArrayInputStream);
            CKryoSerialize kryoSerialization = new CKryoSerialize(pool);
            Object obj = kryoSerialization.deserialize(byteArrayInputStream);
            return obj;
        } finally {
            closer.close();
        }
    }
}
