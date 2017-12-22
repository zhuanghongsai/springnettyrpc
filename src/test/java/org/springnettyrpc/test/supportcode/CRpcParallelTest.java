package org.springnettyrpc.test.supportcode;

import org.apache.commons.lang3.time.StopWatch;
import org.springnettyrpc.supportcode.core.CMessageSendExecutor;
import org.springnettyrpc.supportcode.support.CRpcSerializeProtocol;
import org.springnettyrpc.test.simple.CalcParallelRequestThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author ZHS
 * @create 2017-12-15 14:25
 */
public class CRpcParallelTest {

    public static void parallelTask(CMessageSendExecutor executor, int parallel, String serverAddress, CRpcSerializeProtocol protocol) throws InterruptedException {
        //开始计时
        StopWatch sw = new StopWatch();
        sw.start();

        CountDownLatch signal = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(parallel);

        for (int index = 0; index < parallel; index++) {
            CCalcParallelRequestThread client = new CCalcParallelRequestThread(executor, signal, finish, index);
            new Thread(client).start();
        }

        //10000个并发线程瞬间发起请求操作
        signal.countDown();
        finish.await();
        sw.stop();

        String tip = String.format("[%s] RPC调用总共耗时: [%s] 毫秒", protocol, sw.getTime());
        System.out.println(tip);

    }

    //JDK本地序列化协议
    public static void JdkNativeParallelTask(CMessageSendExecutor executor, int parallel) throws InterruptedException {
        String serverAddress = "127.0.0.1:18887";
        CRpcSerializeProtocol protocol = CRpcSerializeProtocol.JDKSERIALIZE;
        executor.setRpcServerLoader(serverAddress, protocol);
        CRpcParallelTest.parallelTask(executor, parallel, serverAddress, protocol);
        TimeUnit.SECONDS.sleep(3);
    }

    //Kryo序列化协议
    public static void KryoParallelTask(CMessageSendExecutor executor, int parallel) throws InterruptedException {
        String serverAddress = "127.0.0.1:18888";
        CRpcSerializeProtocol protocol = CRpcSerializeProtocol.KRYOSERIALIZE;
        executor.setRpcServerLoader(serverAddress, protocol);
        CRpcParallelTest.parallelTask(executor, parallel, serverAddress, protocol);
        TimeUnit.SECONDS.sleep(3);
    }

    //Hessian序列化协议
    public static void HessianParallelTask(CMessageSendExecutor executor, int parallel) throws InterruptedException {
        String serverAddress = "127.0.0.1:18889";
        CRpcSerializeProtocol protocol = CRpcSerializeProtocol.HESSIANSERIALIZE;
        executor.setRpcServerLoader(serverAddress, protocol);
        CRpcParallelTest.parallelTask(executor, parallel, serverAddress, protocol);
        TimeUnit.SECONDS.sleep(3);
    }

    public static void main(String[] args) throws Exception {
        //并行度10000
        int parallel = 1000;
        CMessageSendExecutor executor = new CMessageSendExecutor();

        for (int i = 0; i < 10; i++) {
            JdkNativeParallelTask(executor, parallel);
            KryoParallelTask(executor, parallel);
            HessianParallelTask(executor, parallel);
            System.out.printf("[author tangjie] Netty RPC Server 消息协议序列化第[%d]轮并发验证结束!\n\n",i );
        }

        executor.stop();
    }
}
