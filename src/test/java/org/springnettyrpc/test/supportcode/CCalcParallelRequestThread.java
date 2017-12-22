package org.springnettyrpc.test.supportcode;


import org.springnettyrpc.supportcode.core.CMessageSendExecutor;
import org.springnettyrpc.supportcode.servicebean.CCalculate;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * 并发线程模拟
 * @author ZHS
 * @create 2017-12-11 17:13
 */
public class CCalcParallelRequestThread implements Runnable {
    private CountDownLatch signal;
    private CountDownLatch finish;
    private CMessageSendExecutor executor;
    private int taskNumber = 0;

    public CCalcParallelRequestThread(CMessageSendExecutor executor, CountDownLatch signal, CountDownLatch finish, int taskNumber) {
        this.signal = signal;
        this.finish = finish;
        this.taskNumber = taskNumber;
        this.executor = executor;
    }

    public void run() {
        try {
            signal.await();

            CCalculate calc = executor.execute(CCalculate.class);
            int add = calc.add(taskNumber, taskNumber);
            System.out.println("calc add result:[" + add + "]");

            finish.countDown();
        } catch (InterruptedException ex) {
            Logger.getLogger(CCalcParallelRequestThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
