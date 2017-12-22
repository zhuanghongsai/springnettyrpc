package org.springnettyrpc.supportcode.hessian;

import org.springnettyrpc.supportcode.support.CMessageCodecUtil;
import org.springnettyrpc.supportcode.support.CMessageDecoder;

/**
 * Hessian解码器
 * @author ZHS
 * @create 2017-12-15 10:51
 */
public class CHessianDecoder extends CMessageDecoder {
    public CHessianDecoder(CMessageCodecUtil util) {
        super(util);
    }
}
