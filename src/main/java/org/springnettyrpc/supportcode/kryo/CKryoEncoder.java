package org.springnettyrpc.supportcode.kryo;

import org.springnettyrpc.supportcode.support.CMessageCodecUtil;
import org.springnettyrpc.supportcode.support.CMessageEncoder;

/**
 * Kryo编码器
 * @author ZHS
 * @create 2017-12-15 10:35
 */
public class CKryoEncoder extends CMessageEncoder {
    public CKryoEncoder(CMessageCodecUtil util) {
        super(util);
    }
}
