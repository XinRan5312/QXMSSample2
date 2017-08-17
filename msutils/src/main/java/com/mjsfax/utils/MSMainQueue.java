/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: MSMainQueue.java
* Author: haorenjie
* Version: 1.0
* Create: 2016-04-20
*
* Changes
* ------------------------------------------------
* 2016-04-20 : 创建  MSMainQueue.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.utils;

import android.os.Handler;
import android.os.Looper;

public class MSMainQueue {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void post(Runnable r) {
        sHandler.post(r);
    }

    public static void postDelaySeconds(Runnable r, long delaySeconds) {
        sHandler.postDelayed(r, delaySeconds * 1000L);
    }

    public static void postDelayMillis(Runnable r, long delayMilliSeconds) {
        sHandler.postDelayed(r, delayMilliSeconds);
    }
}
