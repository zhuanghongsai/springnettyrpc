package org.springnettyrpc.supportcode.hessian;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Hessian序列化/反序列化对象工厂池
 * @author ZHS
 * @create 2017-12-15 10:46
 */
public class CHessianSerializeFactory extends BasePooledObjectFactory<CHessianSerialize> {
    @Override
    public CHessianSerialize create() throws Exception {
        return createHessian();
    }

    @Override
    public PooledObject<CHessianSerialize> wrap(CHessianSerialize obj) {
        return new DefaultPooledObject<CHessianSerialize>(obj);
    }
    private CHessianSerialize createHessian() {
        return new CHessianSerialize();
    }
}
