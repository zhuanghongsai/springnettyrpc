package org.springnettyrpc.supportcode.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.springnettyrpc.supportcode.support.CRpcSerialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Hessian序列化/反序列化实现
 * @author ZHS
 * @create 2017-12-15 10:43
 */
public class CHessianSerialize implements CRpcSerialize {

    public void serialize(OutputStream output, Object object) {
        Hessian2Output ho = new Hessian2Output(output);
        try {
            ho.startMessage();
            ho.writeObject(object);
            ho.completeMessage();
            ho.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(InputStream input) {
        Object result = null;
        try {
            Hessian2Input hi = new Hessian2Input(input);
            hi.startMessage();
            result = hi.readObject();
            hi.completeMessage();
            hi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
