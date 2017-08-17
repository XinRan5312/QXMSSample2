/*
* ------------------------------------------------
* Copyright (C) 2015-2016, by OCEANWIDE HOLDINGS CO., LTD, All rights reserved.
* ------------------------------------------------
*
* File: ConsolePrinter.java
* Author: haorenjie
* Version: 1.0
* Create: 2015-12-13
*
* Changes
* ------------------------------------------------
* 2015-12-13 : 创建  ConsolePrinter.java (haorenjie);
* ------------------------------------------------
*/
package com.mjsfax.logs;

import android.util.Log;

public class ConsolePrinter implements ILogPrinter {
    public static final String TAG = "ConsolePrinter";

    @Override
    public void setMessageFormatter(IMessageFormatter formatter) {

    }

    @Override
    public void print(String message, String fileName, String position, int level) {
        //重复判断一次
        if (fileName == null) {
            return;
        } else if (message == null) {
            return;
        }

        int suffixPos = fileName.lastIndexOf(".");
        String fn = fileName.substring(0, suffixPos);
        final String tag = ((fn == null) ? fileName : fn);
        switch (level) {
            case MSLoggerImpl.LogLevel.VERBOSE: {
                Log.v(tag, message);
            }
            break;
            case MSLoggerImpl.LogLevel.DEBUG: {
                Log.d(tag, message);
            }
            break;
            case MSLoggerImpl.LogLevel.INFO: {
                Log.i(tag, message);
            }
            break;
            case MSLoggerImpl.LogLevel.WARNING: {
                Log.w(tag, message);
            }
            break;
            case MSLoggerImpl.LogLevel.ERROR: {
                Log.e(tag, message);
            }
            break;
            default: {
                Log.w(tag, "Unknown log level: " + level + "at line: " + position);
            }
        }
    }

    @Override
    public int getPrinterType() {
        return MSLoggerImpl.Output.CONSOLE;
    }
}
