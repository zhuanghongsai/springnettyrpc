package org.springnettyrpc.supportcode.kryo;

import org.springnettyrpc.supportcode.support.CMessageCodecUtil;
import org.springnettyrpc.supportcode.support.CMessageDecoder;

/**
 * Kryo解码器
 * @author ZHS
 * @create 2017-12-15 10:35
 */
public class CKryoDecoder extends CMessageDecoder {
    public CKryoDecoder(CMessageCodecUtil util) {
        super(util);
    }
}
