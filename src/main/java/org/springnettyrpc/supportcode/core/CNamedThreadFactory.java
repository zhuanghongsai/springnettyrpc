package org.springnettyrpc.supportcode.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  线程工厂
 * @author ZHS
 * @create 2017-12-15 11:19
 */
public class CNamedThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String prefix;

    private final boolean daemoThread;

    private final ThreadGroup threadGroup;

    public CNamedThreadFactory() {
        this("rpcserver-threadpool-" + threadNumber.getAndIncrement(), false);
    }

    public CNamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public CNamedThreadFactory(String prefix, boolean daemo) {
        this.prefix = prefix + "-thread-";
        daemoThread = daemo;
        // SecurityManager安全管理器
        SecurityManager s = System.getSecurityManager();
        //返回该线程所属的线程组
        threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    public Thread newThread(Runnable runnable) {
        String name = prefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(threadGroup, runnable, name, 0);
        ret.setDaemon(daemoThread);
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
}
