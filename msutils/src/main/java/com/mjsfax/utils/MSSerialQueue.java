/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSSerialQueue.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-04-20
*
* Changes
* ------------------------------------------------
* 2016-04-20 : 创建  MSSerialQueue.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class MSSerialQueue {
    private HandlerThread mHandlerThread;
    private Handler mHandler;

    public MSSerialQueue(String queueName) {
        mHandlerThread = new HandlerThread(queueName);
        mHandlerThread.start();
        Looper looper = mHandlerThread.getLooper();
        mHandler = new Handler(looper);
    }

    public void post(Runnable r) {
        mHandler.post(r);
    }

    public void postDelay(Runnable r, long delaySeconds) {
        mHandler.postDelayed(r, delaySeconds * 1000L);
    }

    public boolean isActive() {
        return mHandlerThread != null && mHandlerThread.isAlive();
    }
}
