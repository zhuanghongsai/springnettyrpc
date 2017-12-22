package org.springnettyrpc.supportcode.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.springnettyrpc.supportcode.support.CRpcSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Kryo序列化/反序列化实现
 * @author ZHS
 * @create 2017-12-15 10:18
 */
public class CKryoSerialize implements CRpcSerialize{
    private KryoPool pool = null;


    public CKryoSerialize(final KryoPool pool) {
        this.pool = pool;
    }

    @Override
    public void serialize(OutputStream output, Object object) throws IOException {
        Kryo kryo = pool.borrow();
        Output out = new Output(output);
        kryo.writeClassAndObject(out, object);
        out.close();
        pool.release(kryo);
    }

    @Override
    public Object deserialize(InputStream input) throws IOException {
        Kryo kryo = pool.borrow();
        Input in = new Input(input);
        Object result = kryo.readClassAndObject(in);
        in.close();
        pool.release(kryo);
        return result;
    }
}
