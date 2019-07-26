package com.none.common.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zl
 * @Date: 2019/7/24 0:02
 */
public class ThreadPollUtil {

    /**
     * 创建工作线程
     * @param pollName
     * @return
     */
    public static ThreadPoolExecutor createThreadPoll(String pollName) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, pollName + "-" + count++);
            }
        };
        return new ThreadPoolExecutor(100, 100, 1,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
