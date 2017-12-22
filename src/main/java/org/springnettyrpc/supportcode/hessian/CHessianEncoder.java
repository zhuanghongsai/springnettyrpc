package org.springnettyrpc.supportcode.hessian;

import org.springnettyrpc.supportcode.support.CMessageCodecUtil;
import org.springnettyrpc.supportcode.support.CMessageEncoder;

/**
 * Hessian编码器
 * @author ZHS
 * @create 2017-12-15 10:52
 */
public class CHessianEncoder extends CMessageEncoder {
    public CHessianEncoder(CMessageCodecUtil util) {
        super(util);
    }
}
