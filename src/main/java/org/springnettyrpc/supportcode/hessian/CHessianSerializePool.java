package org.springnettyrpc.supportcode.hessian;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Hessian序列化/反序列化池
 * @author ZHS
 * @create 2017-12-15 10:49
 */
public class CHessianSerializePool {

    //Netty采用Hessian序列化/反序列化的时候，为了避免重复产生对象，提高JVM内存利用率，故引入对象池技术，经过测试
    //遇到高并发序列化/反序列化的场景的时候，序列化效率明显提升不少。
    private GenericObjectPool<CHessianSerialize> hessianPool;
    private static CHessianSerializePool poolFactory = null;

    private CHessianSerializePool() {
        hessianPool = new GenericObjectPool<CHessianSerialize>(new CHessianSerializeFactory());
    }

    public static CHessianSerializePool getHessianPoolInstance() {
        if (poolFactory == null) {
            synchronized (CHessianSerializePool.class) {
                if (poolFactory == null) {
                    poolFactory = new CHessianSerializePool();
                }
            }
        }
        return poolFactory;
    }

    //预留接口，后续可以通过Spring Property Placeholder依赖注入
    public CHessianSerializePool(final int maxTotal, final int minIdle, final long maxWaitMillis, final long minEvictableIdleTimeMillis) {
        hessianPool = new GenericObjectPool<CHessianSerialize>(new CHessianSerializeFactory());
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        //最大池对象总数
        config.setMaxTotal(maxTotal);
        //最小空闲数
        config.setMinIdle(minIdle);
        //最大等待时间， 默认的值为-1，表示无限等待
        config.setMaxWaitMillis(maxWaitMillis);
        //退出连接的最小空闲时间 默认1800000毫秒
        config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        hessianPool.setConfig(config);
    }

    public CHessianSerialize borrow() {
        try {
            return getHessianPool().borrowObject();
        } catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void restore(final CHessianSerialize object) {
        getHessianPool().returnObject(object);
    }

    public GenericObjectPool<CHessianSerialize> getHessianPool() {
        return hessianPool;
    }
}
