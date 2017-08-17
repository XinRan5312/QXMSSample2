/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSGlobalQueue.java
* Author: haorenjie
* Version: 1.0
* Create: 2017-01-04
*
* Changes
* ------------------------------------------------
* 2017-01-04 : 创建  MSGlobalQueue.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MSGlobalQueue {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCounter = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "MSGlobalQueue #" + mCounter.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sWorkPoolQueue = new LinkedBlockingQueue<>(128);
    private static final Executor sThreadPoolExecutor = new ThreadPoolExecutor(
        CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkPoolQueue, sThreadFactory
    );

    public static void post(Runnable task) {
        sThreadPoolExecutor.execute(task);
    }
}
