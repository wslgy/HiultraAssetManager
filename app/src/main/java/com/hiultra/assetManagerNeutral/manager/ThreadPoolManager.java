package com.hiultra.assetManagerNeutral.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理
 * 
 * @author Tom
 *
 */
public class ThreadPoolManager {
    private static Object LOCK = new Object();
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private ThreadPoolExecutor executor;
    private static ThreadPoolManager mIntance;

    /**
     * 如果调用者指定了corePoolSize，否则使用默认的核心线程数
     * 
     * @param corePoolSize
     */
    private ThreadPoolManager() {
//        int poolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;// 最高效率的线程数
        int poolSize = 1;
        this.corePoolSize = poolSize;
        maximumPoolSize = corePoolSize;
        keepAliveTime = 0;//
    }

    public static ThreadPoolManager getInstance() {
        synchronized (LOCK) {
            if (mIntance == null) {
                mIntance = new ThreadPoolManager();
            }
            return mIntance;
        }
    }

    /**
     * 添加一个任务到线程池中
     * 
     * @param runnable
     */
    public void execute(Runnable runnable) {
        if (runnable == null)
            return;
        if (executor == null || executor.isShutdown()) {
            executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        }
        executor.execute(runnable);
    }

    /**
     * 将任务从线程池中移除
     * 
     * @param runnable
     */
    public void cancel(Runnable runnable) {
        if (runnable != null) {
            executor.remove(runnable);
        }
    }
}
