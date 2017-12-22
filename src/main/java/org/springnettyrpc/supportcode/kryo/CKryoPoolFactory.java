package org.springnettyrpc.supportcode.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springnettyrpc.supportcode.model.CMessageRequest;
import org.springnettyrpc.supportcode.model.CMessageResponse;

/**
 * Kryo对象池工厂
 * @author ZHS
 * @create 2017-12-15 10:31
 */
public class CKryoPoolFactory {
    private static CKryoPoolFactory poolFactory = null;

    private KryoFactory factory = new KryoFactory() {
        public Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setReferences(false);
            //把已知的结构注册到Kryo注册器里面，提高序列化/反序列化效率
            kryo.register(CMessageRequest.class);
            kryo.register(CMessageResponse.class);
            kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    private KryoPool pool = new KryoPool.Builder(factory).build();

    private CKryoPoolFactory() {
    }

    public static KryoPool getKryoPoolInstance() {
        if (poolFactory == null) {
            synchronized (CKryoPoolFactory.class) {
                if (poolFactory == null) {
                    poolFactory = new CKryoPoolFactory();
                }
            }
        }
        return poolFactory.getPool();
    }

    public KryoPool getPool() {
        return pool;
    }
}
