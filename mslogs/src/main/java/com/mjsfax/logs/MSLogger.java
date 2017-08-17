/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: FHLog.java
* Author: Hao Renjie
* Version: 1.0
* Create: 2015-12-04
*
* Changes
* ------------------------------------------------
* 2015-12-04 : 创建 FHLog.java (Hao Renjie);
* ------------------------------------------------
*/
package com.mjsfax.logs;


import android.content.Context;
import android.text.TextUtils;


public class MSLogger {
    /**
     * The initialize method should be called at the beginning of the application start before any
     * log print method called.
     *
     * @param context the application context
     */
    public static void initialize(Context context, boolean isDebug) {
        MSLoggerImpl.getsInstance().initialize(context, isDebug);
    }

    /**
     * @param printLevel @see class MSLoggerImpl.LogLevel
     * @param output     @see class MSLoggerImpl.Output
     */
    public static void setLogConfig(int printLevel, int output) {
        MSLoggerImpl.getsInstance().setLogConfig(printLevel, output);
    }
    private static boolean isMsgEmpty(String message){
        if (TextUtils.isEmpty(message) || message.trim().length() == 0) {
            return true;
        }
        return false;
    }
    public static void v(String message) {
        if(!isMsgEmpty(message))
            MSLoggerImpl.getsInstance().verbose(message);
    }

    public static void d(String message) {
        if(!isMsgEmpty(message))
            MSLoggerImpl.getsInstance().debug(message);
    }

    public static void i(String message) {
        if(!isMsgEmpty(message))
            MSLoggerImpl.getsInstance().info(message);
    }

    public static void w(String message) {
        if(!isMsgEmpty(message))
            MSLoggerImpl.getsInstance().warning(message);
    }

    public static void e(String message) {
        if(!isMsgEmpty(message))
            MSLoggerImpl.getsInstance().error(message);
    }
}
